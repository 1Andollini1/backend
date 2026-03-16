package com.invitation.backend.service;

import com.invitation.backend.dto.request.RsvpCreateRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Service
public class GoogleFormService {

    private static final String FORM_RESPONSE_URL =
            "https://docs.google.com/forms/d/e/1FAIpQLSdNk7e3sleAvD7fGwAs5VuyaTkSfdawBzM5S3e_jg_LgNvzqg/formResponse";

    private final RestClient restClient = RestClient.create();

    public void submit(RsvpCreateRequest request) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();

        formData.add("entry.1498135098", safe(request.fullName));
        formData.add("entry.1062946950", safe(request.phone));
        formData.add("entry.362466491", safe(request.email));
        formData.add("entry.877086558", Boolean.TRUE.equals(request.willCome) ? "Да" : "Нет");
        formData.add("entry.1424661284", safe(request.reason));
        formData.add("entry.2024306556", Boolean.TRUE.equals(request.isNotSingle) ? "Да" : "Нет");
        formData.add("entry.910883600", safe(request.secondGuestName));
        formData.add("entry.2606285", safe(request.questions));
        formData.add("entry.1136362837", safe(request.allergic));

        try {
            ResponseEntity<String> response = restClient.post()
                    .uri(FORM_RESPONSE_URL)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(formData)
                    .retrieve()
                    .toEntity(String.class);

            System.out.println("Google Form status: " + response.getStatusCode());
            System.out.println("Google Form response body: " + response.getBody());
        } catch (Exception e) {
            System.out.println("Google Form submit failed: " + e.getClass().getName());
            System.out.println("Google Form submit failed message: " + e.getMessage());
            throw e;
        }
    }

    private String safe(String value) {
        return value == null ? "" : value.trim();
    }
}
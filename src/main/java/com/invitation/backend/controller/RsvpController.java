package com.invitation.backend.controller;

import com.invitation.backend.dto.request.RsvpCreateRequest;
import com.invitation.backend.dto.response.PageResponse;
import com.invitation.backend.dto.response.RsvpCreateResponse;
import com.invitation.backend.dto.response.RsvpListItemResponse;
import com.invitation.backend.service.RsvpService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RsvpController {

    private final RsvpService service;

    public RsvpController(RsvpService service) {
        this.service = service;
    }

    @PostMapping("/rsvps")
    public ResponseEntity<RsvpCreateResponse> sendRequest(@RequestBody @Valid RsvpCreateRequest request) {
        RsvpCreateResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/rsvps")
    public PageResponse<RsvpListItemResponse> list(
            @RequestParam(required = false) Boolean willCome,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        return service.list(willCome, page, size);
    }
}
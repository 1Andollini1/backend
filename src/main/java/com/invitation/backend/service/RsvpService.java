package com.invitation.backend.service;

import com.invitation.backend.dto.request.RsvpCreateRequest;
import com.invitation.backend.dto.response.PageResponse;
import com.invitation.backend.dto.response.RsvpCreateResponse;
import com.invitation.backend.dto.response.RsvpListItemResponse;
import com.invitation.backend.entity.Rsvp;
import com.invitation.backend.exception.DuplicateRsvpException;
import com.invitation.backend.repository.RsvpRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RsvpService {

    private final RsvpRepository repository;
    private final GoogleFormService googleFormService;

    public RsvpService(RsvpRepository repository, GoogleFormService googleFormService) {
        this.repository = repository;
        this.googleFormService = googleFormService;
    }

    @Transactional
    public RsvpCreateResponse create(RsvpCreateRequest request) {
        String email = normalize(request.email);
        String phone = normalize(request.phone);
        String reason = normalize(request.reason);
        String questions = normalize(request.questions);
        String secondGuestName = normalize(request.secondGuestName);
        String allergic = normalize(request.allergic);

        if (email != null && repository.existsByEmailIgnoreCase(email)) {
            throw new DuplicateRsvpException("RSVP with this email already exists");
        }

        if (phone != null && repository.existsByPhone(phone)) {
            throw new DuplicateRsvpException("RSVP with this phone already exists");
        }

        Rsvp entity = new Rsvp();
        entity.setFullName(request.fullName.trim());
        entity.setEmail(email);
        entity.setPhone(phone);
        entity.setWillCome(request.willCome);
        entity.setReason(reason);
        entity.setQuestions(questions);
        entity.setIsNotSingle(request.isNotSingle);
        entity.setSecondGuestName(secondGuestName);
        entity.setAllergic(allergic);

        Rsvp saved = repository.save(entity);

        try {
            googleFormService.submit(request);
        } catch (Exception e) {
            // позже добавишь нормальный logger
            System.err.println("Failed to submit to Google Form: " + e.getMessage());
        }

        return new RsvpCreateResponse(
                saved.getId(),
                saved.getFullName(),
                saved.getEmail(),
                saved.getPhone(),
                saved.getWillCome(),
                saved.getReason(),
                saved.getCreatedAt(),
                saved.getQuestions(),
                saved.getIsNotSingle(),
                saved.getSecondGuestName(),
                saved.getAllergic()
        );
    }

    public PageResponse<RsvpListItemResponse> list(Boolean willCome, int page, int size) {
        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), 100);

        var pageable = PageRequest.of(safePage, safeSize, Sort.by("createdAt").descending());

        Page<Rsvp> resultPage = (willCome == null)
                ? repository.findAll(pageable)
                : repository.findAllByWillCome(willCome, pageable);

        var items = resultPage.getContent().stream()
                .map(r -> new RsvpListItemResponse(
                        r.getId(),
                        r.getFullName(),
                        r.getEmail(),
                        r.getPhone(),
                        r.getWillCome(),
                        r.getReason(),
                        r.getCreatedAt(),
                        r.getQuestions(),
                        r.getIsNotSingle(),
                        r.getSecondGuestName(),
                        r.getAllergic()
                ))
                .toList();

        return new PageResponse<>(
                items,
                resultPage.getNumber(),
                resultPage.getSize(),
                resultPage.getTotalElements(),
                resultPage.getTotalPages()
        );
    }

    private String normalize(String s) {
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }
}
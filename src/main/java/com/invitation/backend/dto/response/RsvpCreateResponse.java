package com.invitation.backend.dto.response;
import java.time.OffsetDateTime;


public record RsvpCreateResponse(
        Long id,
        String fullName,
        String email,
        String phone,
        Boolean willCome,
        String reason,
        OffsetDateTime createdAt,
        String questions,
        Boolean isNotSingle,
        String secondGuestName,
        String allergic
) {}

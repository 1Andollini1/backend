package com.invitation.backend.exception;

public class DuplicateRsvpException extends RuntimeException {
    public DuplicateRsvpException(String message) {
        super(message);
    }
}

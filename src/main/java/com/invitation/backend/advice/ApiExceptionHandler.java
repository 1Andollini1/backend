package com.invitation.backend.advice;

import com.invitation.backend.exception.DuplicateRsvpException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ValidationErrorResponse handleValidation(MethodArgumentNotValidException ex) {
        List<ValidationErrorResponse.Violation> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::toViolation)
                .toList();

        return new ValidationErrorResponse(400, "Validation failed", errors);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateRsvpException.class)
    public SimpleErrorResponse handleDuplicate(DuplicateRsvpException ex) {
        return new SimpleErrorResponse(409, ex.getMessage());
    }

    private ValidationErrorResponse.Violation toViolation(FieldError fe) {
        return new ValidationErrorResponse.Violation(fe.getField(), fe.getDefaultMessage());
    }

    public record SimpleErrorResponse(int status, String message) {}

    public record ValidationErrorResponse(
            int status,
            String message,
            List<Violation> errors
    ) {
        public record Violation(String field, String message) {}
    }
}
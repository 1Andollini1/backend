package com.invitation.backend.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RsvpCreateRequest {

    @NotBlank
    @Size(max = 100)
    public String fullName;

    @Email
    @Size(max = 200)
    public String email;

    @Size(max = 50)
    public String phone;

    @NotNull
    public Boolean willCome;

    @Size(max = 500)
    public String reason;

    @NotNull
    public boolean isNotSingle;

    public String secondGuestName;

    @Size(max = 500)
    public String allergic;

    @Size(max = 500)
    public String questions;

    @AssertTrue(message = "Either email or phone must be provided")
    public boolean isContactProvided() {
        return (email != null && !email.isBlank())
                || (phone != null && !phone.isBlank());
    }
}
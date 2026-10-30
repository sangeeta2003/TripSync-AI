package com.tripsync.backend.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank(message = "Email is required")
    @Email
    String email;
    @NotBlank(message = "Password is required")
    String password;
}

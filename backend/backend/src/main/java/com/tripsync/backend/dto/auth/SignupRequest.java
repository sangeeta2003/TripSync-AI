package com.tripsync.backend.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SignupRequest {
    @NotBlank(message = "Name is required")
    @Size(max = 100,message = "Name cannot exceed 100 characters")
    String name;
    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email")
    @Size(max = 255)
    String email;
    @NotBlank(message = "Password is required")
    @Size(
            min = 8,
            max = 72,
            message = "Password must contain 8 to 72 characters"
    )
    String password;
}

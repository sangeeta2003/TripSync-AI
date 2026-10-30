package com.tripsync.backend.dto.auth;

import jakarta.validation.constraints.NotBlank;

public class LogoutRequest {
    @NotBlank(message = "Refresh token is required")
    String refreshToken;
}

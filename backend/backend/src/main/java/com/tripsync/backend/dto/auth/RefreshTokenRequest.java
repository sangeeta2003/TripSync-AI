package com.tripsync.backend.dto.auth;

import jakarta.validation.constraints.NotBlank;

public class RefreshTokenRequest {
    @NotBlank(message = "Refresh token is required")
    String refreshToken;
}

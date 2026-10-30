package com.tripsync.backend.dto.auth;

public class AuthResponse {
    String tokenType;
    String accessToken;
    String refreshToken;
    long expiresIn;
    UserResponse user;
}

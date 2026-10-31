package com.tripsync.backend.dto.auth;

public class AuthResponse {

    private String tokenType;
    private String accessToken;
    private String refreshToken;
    private long expiresIn;
    private UserResponse user;

    public AuthResponse(
            String tokenType,
            String accessToken,
            String refreshToken,
            long expiresIn,
            UserResponse user
    ) {
        this.tokenType = tokenType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.user = user;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public UserResponse getUser() {
        return user;
    }
}
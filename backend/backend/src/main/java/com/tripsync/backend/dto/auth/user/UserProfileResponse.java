
package com.tripsync.backend.dto.auth.user;

import java.time.Instant;
import java.util.UUID;

public class UserProfileResponse {

    private UUID id;
    private String name;
    private String email;
    private Instant createdAt;

    public UserProfileResponse(
            UUID id,
            String name,
            String email,
            Instant createdAt
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}

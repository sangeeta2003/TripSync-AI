package com.tripsync.backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {
    private final SecretKey key;
    private final long ttl;

    public JwtService(@Value("${app.jwt.secret}") String secret, @Value("${app.jwt.minutes}") long minutes) {
        if (secret.getBytes(StandardCharsets.UTF_8).length < 32)
            throw new IllegalArgumentException("JWT_SECRET must be at least 32 bytes");
        key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        ttl = minutes * 60000;
    }

    public String create(String email) {
        Date now = new Date();
        return Jwts.builder().subject(email).issuedAt(now).expiration(new Date(now.getTime() + ttl)).signWith(key)
                .compact();
    }

    public String email(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public boolean valid(String token) {
        try {
            email(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

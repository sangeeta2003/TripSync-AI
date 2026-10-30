package com.tripsync.backend.security;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {
    private final SecretKey secretKey;
    private final long accessTokenExpiration;

    public JwtService(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.access-token-expiration}") long expiration
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);

        if (keyBytes.length < 32) {
            throw new IllegalArgumentException(
                    "JWT secret must contain at least 32 bytes"
            );
        }

        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpiration = expiration;
    }

    public String generateAccessToken(UserDetails userDetails) {

        Instant now = Instant.now();

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(now))
                .expiration(
                        Date.from(now.plusMillis(accessTokenExpiration))
                )
                .signWith(secretKey)
                .compact();
    }

    public String extractUsername(String token) {

        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(
            String token,
            UserDetails userDetails
    ) {

        try {
            String username = extractUsername(token);

            return username.equals(userDetails.getUsername())
                    && extractAllClaims(token)
                    .getExpiration()
                    .after(new Date());

        } catch (JwtException | IllegalArgumentException exception) {
            return false;
        }
    }

    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public long getAccessTokenExpiration() {
        return accessTokenExpiration;
    }
}

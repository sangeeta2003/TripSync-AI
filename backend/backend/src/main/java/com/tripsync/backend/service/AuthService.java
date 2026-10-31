
package com.tripsync.backend.service;

import com.tripsync.backend.dto.auth.*;
import com.tripsync.backend.model.RefreshToken;
import com.tripsync.backend.model.User;
import com.tripsync.backend.repository.RefreshTokenRepository;
import com.tripsync.backend.repository.UserRepository;
import com.tripsync.backend.security.JwtService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.HexFormat;

@Service
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    private final long refreshTokenExpiration;

    private final SecureRandom secureRandom = new SecureRandom();

    // CONSTRUCTOR

    public AuthService(
            UserRepository userRepository,
            RefreshTokenRepository refreshTokenRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            @Value("${app.jwt.refresh-token-expiration}")
            long refreshTokenExpiration
    ) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    // 1. SIGNUP

    public AuthResponse signUp(SignupRequest request) {

        String email = request.email()
                .trim()
                .toLowerCase();

        // Check whether email already exists
        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email is already registered"
            );
        }

        // Create new user
        User user = new User();

        user.setName(request.name().trim());
        user.setEmail(email);

        // Encode password before saving
        user.setPasswordHash(
                passwordEncoder.encode(request.password())
        );

        // Save user in database
        User savedUser = userRepository.save(user);

        // Generate tokens and return response
        return generateAuthResponse(savedUser);
    }

    // 2. LOGIN

    public AuthResponse login(LoginRequest request) {

        String email = request.email()
                .trim()
                .toLowerCase();

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            email,
                            request.password()
                    )
            );

        } catch (AuthenticationException exception) {

            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid email or password"
            );
        }

        // Find authenticated user
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.UNAUTHORIZED,
                                "Invalid email or password"
                        )
                );

        return generateAuthResponse(user);
    }

    // 3. REFRESH TOKEN

    public AuthResponse refresh(RefreshTokenRequest request) {

        // Hash the incoming refresh token
        String tokenHash = hashToken(request.refreshToken());

        // Find active refresh token in database
        RefreshToken storedToken = refreshTokenRepository
                .findByTokenHashAndRevokedFalse(tokenHash)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.UNAUTHORIZED,
                                "Invalid refresh token"
                        )
                );

        // Check expiration
        if (!storedToken.getExpiresAt().isAfter(Instant.now())) {

            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Refresh token has expired"
            );
        }

        User user = storedToken.getUser();

        // Revoke old refresh token
        storedToken.setRevoked(true);

        // Generate new access and refresh tokens
        return generateAuthResponse(user);
    }

    // 4. LOGOUT

    public void logout(LogoutRequest request) {

        String tokenHash = hashToken(request.refreshToken());

        refreshTokenRepository
                .findByTokenHashAndRevokedFalse(tokenHash)
                .ifPresent(token -> token.setRevoked(true));
    }

    // 5. COMMON TOKEN GENERATION

    private AuthResponse generateAuthResponse(User user) {

        // Prepare Spring Security user details
        var userDetails =
                org.springframework.security.core.userdetails.User
                        .withUsername(user.getEmail())
                        .password(user.getPasswordHash())
                        .authorities("ROLE_USER")
                        .build();

        // Generate access token
        String accessToken =
                jwtService.generateAccessToken(userDetails);

        // Generate raw refresh token
        String rawRefreshToken = generateRefreshToken();

        // Create refresh token database record
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(user);
        refreshToken.setTokenHash(hashToken(rawRefreshToken));

        refreshToken.setExpiresAt(
                Instant.now().plusMillis(refreshTokenExpiration)
        );

        refreshToken.setRevoked(false);

        // Save hashed refresh token
        refreshTokenRepository.save(refreshToken);

        // Prepare user response
        UserResponse userResponse = new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail()
        );

        // Return authentication response
        return new AuthResponse(
                "Bearer",
                accessToken,
                rawRefreshToken,
                jwtService.getAccessTokenExpiration() / 1000,
                userResponse
        );
    }

    // 6. GENERATE RANDOM REFRESH TOKEN

    private String generateRefreshToken() {

        byte[] randomBytes = new byte[32];

        secureRandom.nextBytes(randomBytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(randomBytes);
    }

    // 7. HASH REFRESH TOKEN

    private String hashToken(String token) {

        try {

            MessageDigest digest =
                    MessageDigest.getInstance("SHA-256");

            byte[] hash = digest.digest(
                    token.getBytes(StandardCharsets.UTF_8)
            );

            return HexFormat.of().formatHex(hash);

        } catch (NoSuchAlgorithmException exception) {

            throw new IllegalStateException(
                    "SHA-256 algorithm is unavailable",
                    exception
            );
        }
    }
}

package com.tripsync.backend.controller;

import com.tripsync.backend.dto.auth.*;
import com.tripsync.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService){
        this.authService = authService;
    }

//    signup
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@Valid @RequestBody SignupRequest request){
        AuthResponse response   = authService.signUp(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }
//    Login
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request){
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
//    Refresh token
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@Valid @RequestBody RefreshTokenRequest request){
        AuthResponse response = authService.refresh(request);
        return ResponseEntity.ok(response);
    }
//    logout
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody LogoutRequest request){
        authService.logout(request);
        return ResponseEntity.noContent().build();
    }
}

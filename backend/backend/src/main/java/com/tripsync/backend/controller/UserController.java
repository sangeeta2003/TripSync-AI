package com.tripsync.backend.controller;

import com.tripsync.backend.dto.auth.user.ChangePasswordRequest;
import com.tripsync.backend.dto.auth.user.UpdateProfileRequest;
import com.tripsync.backend.dto.auth.user.UserProfileResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMyProfile(
            @AuthenticationPrincipal UserDetails userDetails
            ){
        UserProfileResponse response = userService.getMyProfile(userDetails.getUsername());
        return ResponseEntity.ok(response);
    }
    @PutMapping("/me")
    public ResponseEntity<UserProfileResponse> updateMyProfile(@AuthenticationPrincipal UserDetails userDetails,
                                                               @Valid @RequestBody UpdateProfileRequest request){
        UserProfileResponse response = userService.updateProfile(userDetails.getUsername(),request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/me")
    public ResponseEntity<Void> changePassword(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody ChangePasswordRequest request){
        userService.changePassword(
                userDetails.getUsername(),
                request
        );
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/me")
    public ResponseEntity<Void> deactivateAccount(@AuthenticationPrincipal UserDetails userDetails){
        userService.deactivateAccount(userDetails.getUsername());
        return ResponseEntity.noContent().build();

    }
}

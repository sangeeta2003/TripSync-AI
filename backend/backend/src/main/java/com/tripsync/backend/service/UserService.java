package com.tripsync.backend.service;

import com.tripsync.backend.dto.auth.user.ChangePasswordRequest;
import com.tripsync.backend.dto.auth.user.UpdateProfileRequest;
import com.tripsync.backend.dto.auth.user.UserProfileResponse;
import com.tripsync.backend.model.RefreshToken;
import com.tripsync.backend.model.User;
import com.tripsync.backend.repository.RefreshTokenRepository;
import com.tripsync.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.apache.catalina.LifecycleState;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(
            UserRepository userRepository,
            RefreshTokenRepository refreshTokenRepository,
            PasswordEncoder passwordEncoder
    ){
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
    }
//    get loggedin user
    public UserProfileResponse getMyProfile(String email){
        User user = findActiveUser(email);
        return mapToProfileResponse(user);

    }
//    update profile
    public  UserProfileResponse updateProfile(String email, UpdateProfileRequest request){
        User user = findActiveUser(email);
        user.setName(request.getName().trim());
        User updatedUser = userRepository.save(user);
        return mapToProfileResponse(updatedUser);
    }
//    change-password
    public void changePassword(String email, ChangePasswordRequest request){
        User user = findActiveUser(email);
        if(!passwordEncoder.matches(
                request.getCurrentPassword(),
                user.getPasswordHash()
        )){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Current password is incorrect"
            );
        }
        if (passwordEncoder.matches(request.getNewPassword(), user.getPasswordHash())) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "New password must be different"
            );
        }
        user.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        revokeAllRefreshTokens(user);
    }
//    DEACTIVATE account
    public void deactivateAccount(String email){
        User user = findActiveUser(email);
        user.setActive(false);
        userRepository.save(user);
        revokeAllRefreshTokens(user);
    }
    // COMMON METHOD: FIND ACTIVE USER
    public  User findActiveUser(String email){
        User user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(()->
                    new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found"));

        if(!user.isActive()){
            throw  new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Your account is deactivated"

            );


        }
        return user;
    }
//    REVOKE REFRESH TOKENS
    private void revokeAllRefreshTokens(User user){
        List<RefreshToken> tokens = refreshTokenRepository.findAllByUser_IdAndRevokedFalse(user.getId());
        for(RefreshToken token:tokens){
            token.setRevoked(true);

        }
        refreshTokenRepository.saveAll(tokens);
    }
    private UserProfileResponse mapToProfileResponse(User user){
        return new UserProfileResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt()
        );
    }

}

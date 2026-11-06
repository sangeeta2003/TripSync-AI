package com.tripsync.backend.service;

import com.tripsync.backend.model.User;
import com.tripsync.backend.repository.UserRepository;
import org.springframework.boot.actuate.endpoint.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUser {
    private final UserRepository userRepository;
    public CurrentUser(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    public User get(){
        String e = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmailIgnoreCase(e).orElseThrow(() -> new java.util.NoSuchElementException("User not found"));
    }
}

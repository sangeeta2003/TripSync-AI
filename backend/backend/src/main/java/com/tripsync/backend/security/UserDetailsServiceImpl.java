package com.tripsync.backend.security;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository repo;

    public UserDetailsServiceImpl(UserRepository r) {
        repo = r;
    }

    public UserDetails loadUserByUsername(String email) {
        var u = repo.findByEmailIgnoreCase(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return User.withUsername(u.getEmail()).password(u.getPasswordHash()).disabled(!u.isEnabled())
                .authorities("ROLE_USER").build();
    }
}

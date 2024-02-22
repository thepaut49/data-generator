package com.thepaut.backend.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class UserService {

    private UserService() {
        throw new IllegalStateException("Utility class");
    }

    public static String getUserId() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var jwt = (Jwt) authentication.getPrincipal();
        return jwt.getClaimAsString("sub");
    }
}

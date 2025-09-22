package com.example.util;

import com.example.config.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SpringSecurityUtil {

    public static CustomUserDetails getCurrentProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        return user;
    }

    public static Long getProfileId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof CustomUserDetails user) {
            return user.getProfile().getId();
        } else if (principal instanceof String) {
            String username = (String) principal;
        }
        return null;
    }

    public static Long getId() {
        CustomUserDetails user = getCurrentProfile();
        return user.getProfile().getId();
    }
}

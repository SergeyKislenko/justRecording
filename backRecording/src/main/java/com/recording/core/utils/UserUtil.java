package com.recording.core.utils;

import org.springframework.security.core.Authentication;

public class UserUtil {
    public static boolean isAdmin(Authentication auth){
       return auth != null && auth.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ADMIN"));
    }
}

package com.ankita.Rider.util;

import org.springframework.security.core.Authentication;

public class RoleUtil {

    public static boolean isUser(Authentication auth) {
        return auth != null &&
                auth.getAuthorities()
                        .stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_USER"));
    }

    public static boolean isDriver(Authentication auth) {
        return auth != null &&
                auth.getAuthorities()
                        .stream()
                        .anyMatch(a -> a.getAuthority().equals("ROLE_DRIVER"));
    }

}

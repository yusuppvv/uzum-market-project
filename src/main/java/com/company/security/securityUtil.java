package com.company.security;

import com.company.user.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class securityUtil {

    public static User getUser() {
        return (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

    public static Integer getUserId() {
        return ((User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal())
                .getId();
    }
}

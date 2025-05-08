package com.company.security;

import com.company.users.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class securityUtil {

    public static UserEntity getUser() {
        return (UserEntity) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

    public static UUID getUserId() {
        return ((UserEntity) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal())
                .getId();
    }
}

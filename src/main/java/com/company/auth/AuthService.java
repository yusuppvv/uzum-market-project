package com.company.auth;

import com.company.auth.dto.AuthCreation;
import com.company.auth.dto.AuthResponse;
import com.company.component.ApiResponse;
import com.company.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public ApiResponse<AuthResponse> register(AuthCreation value) {
        return null;
    }
}

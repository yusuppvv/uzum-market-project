package com.company.auth;

import com.company.auth.dto.AuthCreation;
import com.company.auth.dto.AuthLogin;
import com.company.auth.dto.AuthResponse;
import com.company.auth.dto.AuthVerification;
import com.company.component.ApiResponse;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PermitAll
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @PermitAll
    public ResponseEntity<ApiResponse<AuthResponse>> register(@RequestBody AuthCreation authCreation) {
        return ResponseEntity.ok(authService.register(authCreation));
    }

    @PostMapping("/verification")
    @PermitAll
    public ResponseEntity<ApiResponse<AuthResponse>> verification(@RequestBody AuthVerification authVerification) {
        return ResponseEntity.ok(authService.verification(authVerification));
    }

    @PostMapping("/login")
    @PermitAll
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody AuthLogin authLogin) {
        return ResponseEntity.ok(authService.login(authLogin));
    }
}

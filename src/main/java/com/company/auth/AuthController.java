package com.company.auth;


import com.company.auth.dto.AuthDto;
import com.company.auth.dto.RegisterDto;
import com.company.auth.dto.VerifyDto;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PermitAll
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterDto registerDto) {
        return authService.register(registerDto);
    }

    @PermitAll
    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody VerifyDto verifyDto) {
        return authService.verify(verifyDto);
    }

    @PermitAll
    @PostMapping("/login")
    public ResponseEntity<AuthResp> login(@RequestBody AuthDto authDto) {
        return authService.login(authDto);
    }

}

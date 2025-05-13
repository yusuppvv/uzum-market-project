package com.company.auth;

import com.company.auth.dto.AuthCreation;
import com.company.auth.dto.AuthLogin;
import com.company.auth.dto.AuthResponse;
import com.company.auth.dto.AuthVerification;
import com.company.auth.verfication.VerificationEntity;
import com.company.auth.verfication.VerificationRepository;
import com.company.component.ApiResponse;
import com.company.component.Components;
import com.company.component.smpt.SmptService;
import com.company.component.smpt.Sms;
import com.company.security.jwtUtil;
import com.company.users.Role;
import com.company.users.Status;
import com.company.users.UserEntity;
import com.company.users.UserRepository;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final SmptService smsService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Map<String, AuthCreation> authCreationMap = new HashMap<>();
    private final VerificationRepository verificationCodes;
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public ApiResponse<AuthResponse> register(AuthCreation authCreation) {
        Optional<UserEntity> optionalUser = userRepository.findByEmailAndVisibilityTrue(authCreation.getEmail());
        if (optionalUser.isPresent()) {
            return new ApiResponse<>(404, "User already exists");
        }
        try {
            int i = (int) (Math.random() * 900000) + 100000;
            smsService.sendSms(buildSms(authCreation.getEmail(), "Verificate your email" , Components.SMS_1 + authCreation.getFullName() + Components.SMS_2 + i));
            verificationCodes.save(VerificationEntity.builder()
                    .email(authCreation.getEmail())
                    .code(i)
                    .visibility(true)
                    .build());
            authCreationMap.put(authCreation.getEmail(), authCreation);
            return new ApiResponse<>(200, "Verification code sent to your email successfully. To verify your email, please check the website below:\n http://localhost:8080/api/v1/auth/verify");
        } catch (Exception e) {
            return new ApiResponse<>(404, "Error sending verification code");
        }
    }

    public ApiResponse<AuthResponse> verification(AuthVerification authVerification) {
        Optional<VerificationEntity> optional = verificationCodes.findByEmailAndVisibilityTrue(authVerification.getEmail());
        if (optional.isPresent()) {
            if (optional.get().getCode() == authVerification.getCode()) {
                String password = authCreationMap.get(authVerification.getEmail()).getPassword();
                if (password.isEmpty()) {
                    return new ApiResponse<>(404, "Password is empty");
                }
                UserEntity user = UserEntity.builder()
                        .fullName(authCreationMap.get(authVerification.getEmail()).getFullName())
                        .email(authVerification.getEmail())
                        .password(passwordEncoder.encode(password))
                        .role(Role.USER)
                        .status(Status.ACTIVE)
                        .build();
                System.out.println("user = " + user);
                AuthResponse authResponse = AuthResponse.builder()
                        .email(authVerification.getEmail())
                        .token(jwtUtil.encode(authVerification.getEmail(), user.getRole()))
                        .build();
                authCreationMap.remove(authVerification.getEmail());
                optional.get().setVisibility(false);
                userRepository.save(user);
                return new ApiResponse<>(200, "User registered successfully", authResponse);
            }
            else {
                return new ApiResponse<>(404, "Verification code is not correct");
            }
        }
        return new ApiResponse<>(404, "Verification code is not correct");
    }

    public ApiResponse<AuthResponse> login(AuthLogin authLogin) {
        Optional<UserEntity> optional = userRepository.findByEmailAndVisibilityTrue(authLogin.getEmail());
        if (optional.isPresent()) {
            String password = optional.get().getPassword();
            System.out.println("password = " + password);
            System.out.println("passwordEncoder.matches(authLogin.getPassword(), optional.get().getPassword()) = " + passwordEncoder.matches(authLogin.getPassword(), optional.get().getPassword()));
            if (passwordEncoder.matches(authLogin.getPassword(), password)) {
                AuthResponse authResponse = AuthResponse.builder()
                        .email(authLogin.getEmail())
                        .token(jwtUtil.encode(authLogin.getEmail(), optional.get().getRole()))
                        .build();
                return new ApiResponse<>(200, "User logged in successfully", authResponse);
            }
            else {
                return new ApiResponse<>(404, "Password is not correct");
            }
        }
        else {
            return new ApiResponse<>(404, "User not found");
        }
    }

    private Sms buildSms(String email, String subject, String text) {
        return Sms.builder()
                .text(text)
                .subject(subject)
                .to(email)
                .build();
    }
}

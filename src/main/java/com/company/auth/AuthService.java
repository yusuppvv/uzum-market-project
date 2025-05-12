package com.company.auth;

import com.company.auth.dto.AuthCreation;
import com.company.auth.dto.AuthLogin;
import com.company.auth.dto.AuthResponse;
import com.company.auth.dto.AuthVerification;
import com.company.component.ApiResponse;
import com.company.component.Components;
import com.company.component.smpt.SmptService;
import com.company.component.smpt.Sms;
import com.company.security.jwtUtil;
import com.company.users.Role;
import com.company.users.Status;
import com.company.users.UserEntity;
import com.company.users.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final SmptService smsService;
    private final BCryptPasswordEncoder passwordEncoder;
    private Map<String, AuthCreation> authCreationMap = new HashMap<>();
    private Map<String, Integer> verificationCodes = new HashMap<>();

    public ApiResponse<AuthResponse> register(AuthCreation authCreation) {
        Optional<UserEntity> optionalUser = userRepository.findByEmailAndVisibilityTrue(authCreation.getEmail());
        if (optionalUser.isPresent()) {
            return new ApiResponse<>(404, "User already exists");
        }
        else {
            int i = (int) ((Math.random() * 900000) + 100000);
            Sms sms = new Sms();
            sms.setTo(authCreation.getEmail());
            sms.setSubject("Verification Code");
            sms.setText(Components.SMS_1 + authCreation.getFullName() + Components.SMS_2 + i);
            Boolean isSuccessfull = smsService.sendSms(sms);
            if (isSuccessfull) {
                verificationCodes.put(authCreation.getEmail(), i);
                authCreationMap.put(authCreation.getEmail(), authCreation);
                return new ApiResponse<>(200, Components.SMS_SUCCESSFULL);
            } else {
                return new ApiResponse<>(500, "Error occurred while sending verification code");
            }
        }
    }

    public ApiResponse<AuthResponse> verification(AuthVerification authVerification) {
        Integer i = verificationCodes.get(authVerification.getEmail());
        if (i == null) {
            return new ApiResponse<>(404, "Verification code not found");
        }
        if (i == authVerification.getCode()) {
            AuthCreation authCreation = authCreationMap.get(authVerification.getEmail());
            UserEntity userEntity = UserEntity
                    .builder()
                    .email(authVerification.getEmail())
                    .role(Role.USER)
                    .password(passwordEncoder.encode(authCreation.getPassword()))
                    .status(Status.ACTIVE)
                    .fullName(authCreation.getFullName())
                    .build();
            userRepository.save(userEntity);
            verificationCodes.remove(authVerification.getEmail());
            authCreationMap.remove(authVerification.getEmail());
            return new ApiResponse<>(200, Components.VERIFICATION_SUCCESS);
        }
        else {
            return new ApiResponse<>(404, "Verification code or email is incorrect");
        }
    }

    public ApiResponse<AuthResponse> login(AuthLogin authLogin) {
        Optional<UserEntity> optionalUser = userRepository.findByEmailAndVisibilityTrue(authLogin.getEmail());
        if (optionalUser.isPresent()) {
            UserEntity userEntity = optionalUser.get();
            System.out.println("userEntity.getPassword() = " + userEntity.getPassword());
            if (userEntity.getEmail().equals(authLogin.getEmail())
                    &&
                    passwordEncoder.matches(authLogin.getPassword(), userEntity.getPassword())) {
                AuthResponse authResponse = new AuthResponse();
                authResponse.setEmail(authLogin.getEmail());
                authResponse.setToken(jwtUtil.encode(authResponse.getEmail(), userEntity.getRole()));
                return new ApiResponse<>(200, "Successfully logged in", authResponse);
            }
            else {
                return new ApiResponse<>(404, "Password or email is incorrect.");
            }
        }
        else return new ApiResponse<>(404, "Password or email is incorrect.");
    }
}

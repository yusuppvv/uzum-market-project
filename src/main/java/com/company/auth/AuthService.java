package com.company.auth;

import com.company.auth.dto.AuthDto;
import com.company.auth.dto.RegisterDto;
import com.company.auth.dto.VerifyDto;
import com.company.exception.InvalidPasswordOrUsernameException;
import com.company.security.jwtUtil;
import com.company.smpt.SmptService;
import com.company.smpt.Sms;
import com.company.user.User;
import com.company.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final Map<String, Integer> verificationCodes = new HashMap<>();
    private RegisterDto registerDto;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SmptService smptService;

    public ResponseEntity<?> verify(VerifyDto verifyDto) {
        Integer savedCode = verificationCodes.get(verifyDto.getEmail());
        if (savedCode == null) {
            return ResponseEntity.badRequest()
                    .body("Email or code is incorrect!");
        }
        if (!savedCode.toString().equals(verifyDto.getCode())) {
            return ResponseEntity.badRequest()
                    .body("Email or code is incorrect!");
        }

        User user = User.builder()
                .name(registerDto.getName())
                .email(verifyDto.getEmail())
                .build();

        User savedUser = userRepository.save(user);
        verificationCodes.remove(verifyDto.getEmail());

        return ResponseEntity.ok(new AuthResp(
                savedUser.getEmail(),
                jwtUtil.encode(savedUser.getEmail(), savedUser.getRole())
        ));
    }

    public ResponseEntity<?> register(RegisterDto registerDto) {
        Optional<User> optionalUser = userRepository
                .findByEmailOrUsername(registerDto.getGmail(), registerDto.getUsername());
        if (optionalUser.isPresent())
            return ResponseEntity.status(400).body("It seems to user already exists please login and comeback.");
        else {
            try {
                this.registerDto = registerDto;
                Integer code = Integer.parseInt(generateVerificationCode());
                verificationCodes.put(registerDto.getGmail(), code);
                Sms sms = new Sms();
                sms.setTo(registerDto.getGmail());
                sms.setSubject("Verification");
                sms.setText("Hi " + registerDto.getName() + ", Your verification code is: " + code);
                smptService.sendSms(sms);
                return ResponseEntity.ok("Verification code sent to your email. " +
                        "Tap to this link to verify: http://localhost:8080/api/v1/auth/verify");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return ResponseEntity.status(400).body("Something went wrong please try again later.");
            }
        }
    }

    private String generateVerificationCode() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append((int) (Math.random() * 10));
        }
        return code.toString();
    }
    public ResponseEntity<AuthResp> login(AuthDto authDto) {

        User user = getByEmail(authDto.getGmail());

        if (user.getEmail().equals(authDto.getGmail()) &&
        bCryptPasswordEncoder.matches(authDto.getPassword(), (user.getPassword()))) {

            return ResponseEntity.ok(
                    new AuthResp(
                            user.getEmail(),
                            jwtUtil.encode(user.getEmail(), user.getRole())
                    )
            );

        }
        throw new InvalidPasswordOrUsernameException("Invalid password or username.");
    }

    private User getByEmail(String username) {
        return userRepository
                .findByEmail(username)
                .orElseThrow();
    }


}

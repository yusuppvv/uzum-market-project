package com.company.auth;

import com.company.auth.dto.AuthDto;
import com.company.auth.dto.RegisterDto;
import com.company.auth.dto.VerifyDto;
import com.company.exception.InvalidPasswordOrUsernameException;
import com.company.exception.ItemNotFoundException;
import com.company.security.jwtUtil;
import com.company.smpt.SmptService;
import com.company.smpt.Sms;
import com.company.user.Role;
import com.company.user.User;
import com.company.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private Map<String, Integer> verificationCodes = new HashMap<>();
    private RegisterDto registerDto;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SmptService smptService;

    public ResponseEntity<?> verify(VerifyDto verifyDto) {
        Integer savedCode = verificationCodes.get(verifyDto.getEmail());
        if (savedCode == null) {
            return ResponseEntity.badRequest()
                    .body("Code not found");
        }
        if (!savedCode.toString().equals(verifyDto.getCode().toString())) {
            return ResponseEntity.badRequest()
                    .body("Your code is incorrect!");
        }

        User user = User.builder()
                .name(registerDto.getName())
                .role(Role.USER)
                .gmail(registerDto.getGmail())
                .password(bCryptPasswordEncoder.encode(registerDto.getPassword()))
                .build();

        User saved = userRepository.save(user);
        verificationCodes.remove(verifyDto.getEmail());

        return ResponseEntity.ok(new AuthResp(
                saved.getGmail(),
                jwtUtil.encode(saved.getGmail(), saved.getRole())
        ));
    }

    public ResponseEntity<?> register(RegisterDto registerDto) {
        Optional<User> optionalUser = userRepository
                .findByGmail(registerDto.getGmail());
        if (optionalUser.isPresent()) return ResponseEntity.status(400).body("Item Already Exists!!!");
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
                e.printStackTrace();
                return ResponseEntity.status(400).body("Please try again later.");
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

        User user =  getByUsername(authDto.getGmail());

        if (user.getGmail().equals(authDto.getGmail()) &&
        bCryptPasswordEncoder.matches(authDto.getPassword(), (user.getPassword()))) {

            return ResponseEntity.ok(
                    new AuthResp(
                            user.getGmail(),
                            jwtUtil.encode(user.getGmail(), user.getRole())
                    )
            );

        }

        throw new InvalidPasswordOrUsernameException("Invalid password or username.");

    }

    private User getByUsername(String username) {
        return userRepository
                .findByGmail(username)
                .orElseThrow();
    }


}

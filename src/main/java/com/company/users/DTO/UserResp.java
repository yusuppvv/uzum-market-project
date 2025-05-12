package com.company.users.DTO;

import com.company.security.jwtUtil;
import com.company.users.UserEntity;
import com.company.users.UserRepository;
import com.company.users.UserService;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResp {
    private UUID id;
    private String fullName;
    private String email;
    private String token;

    public UserResp(String email, String fullName) {
        this.email = email;
        this.fullName = fullName;
    }
}

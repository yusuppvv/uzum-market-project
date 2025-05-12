package com.company.auth.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthVerification {
    @Email(message = "Email is not valid to the pattern. Please check the email again")
    private String email;
    private int code;
}

package com.company.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthLogin {
    @Email(message = "Email should be valid to the format")
    private String email;
    @NotBlank(message = "Password should not be blank")
    private String password;
}

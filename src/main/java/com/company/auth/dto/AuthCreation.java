package com.company.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class AuthCreation {
    @NotBlank(message = "Full name should not be blank")
    private String fullName;
    @Email(message = "Email should be valid to the pattern. Example: example@example.com")
    private String email;
    private String password;

    public AuthCreation(String fullName, String email, String password) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }
}

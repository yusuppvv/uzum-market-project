package com.company.users.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserCreationDto {

    @NotBlank(message = "fullName must not blank!!!")
    @NotNull(message = "fullName must not null!!!")
    private String fullName;

    @Email(message = "Email Must be in Pattern!!!")
    private String email;
}

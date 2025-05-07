package com.company.users.DTO;

import com.company.component.Components;
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

    @NotBlank(message = Components.NOT_BLANK)
    @NotNull(message = Components.NOT_NULL)
    private String fullName;

    @Email(message = Components.EMAIL)
    private String email;
}

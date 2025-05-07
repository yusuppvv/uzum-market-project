package com.company.category.DTO;

import com.company.component.Components;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryCr {
    @NotBlank(message = Components.NOT_BLANK)
    @NotNull(message = Components.NOT_NULL)
    private String name;
}

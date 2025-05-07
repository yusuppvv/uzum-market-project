package com.company.cart.DTO;

import com.company.component.Components;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import java.util.UUID;
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartCr {
    @NotBlank(message = Components.NOT_BLANK)
    @NotNull(message = Components.NOT_NULL)
    private Integer quantity;

    @NotBlank(message = Components.NOT_BLANK)
    @NotNull(message = Components.NOT_NULL)
    private UUID userId;

    @NotBlank(message = Components.NOT_BLANK)
    @NotNull(message = Components.NOT_NULL)
    private UUID productId;
}

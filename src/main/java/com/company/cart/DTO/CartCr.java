package com.company.cart.DTO;

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
    @NotBlank(message = "quantity must not blank!!!")
    @NotNull(message = "quantity must not null!!!")
    private Integer quantity;

    @NotBlank(message = "userId must not blank!!!")
    @NotNull(message = "userId must not null!!!")
    private UUID userId;

    @NotBlank(message = "productId must not blank!!!")
    @NotNull(message = "productId must not null!!!")
    private UUID productId;
}

package com.company.cart.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartCr {
    private Integer quantity;
    private UUID userId;
    private UUID productId;
}

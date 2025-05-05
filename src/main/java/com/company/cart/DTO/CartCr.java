package com.company.cart.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;
@Data
@Builder
public class CartCr {
    private Integer quantity;
    private UUID userId;
    private UUID productId;
}

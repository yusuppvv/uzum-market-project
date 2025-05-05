package com.company.cart.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartCreation {
    private Integer quantity;
    private UUID userId;
    private UUID orderId;
    private UUID productId;
}

package com.company.cart.DTO;

import java.util.UUID;

public record CartResp(
        UUID id,
        Integer quantity,
        UUID userId,
        UUID productId
) {
}

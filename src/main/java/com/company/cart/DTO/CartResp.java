package com.company.cart.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CartResp(
        UUID id,
        Integer quantity,
        UUID userId,
        UUID productId
) {
}

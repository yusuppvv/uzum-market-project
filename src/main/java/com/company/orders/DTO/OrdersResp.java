package com.company.orders.DTO;

import com.company.orders.Status;
import com.company.orders.Type;

import java.math.BigDecimal;
import java.util.UUID;

public record OrdersResp(
        UUID id,
        BigDecimal totalPrice,
        Status status,
        Type type,
        UUID userId
) {
}

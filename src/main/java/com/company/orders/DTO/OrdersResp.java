package com.company.orders.DTO;

import com.company.orders.Status;
import com.company.orders.Type;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.UUID;
@JsonInclude(JsonInclude.Include.NON_NULL)
public record OrdersResp(
        UUID id,
        BigDecimal totalPrice,
        Status status,
        Type type,
        UUID userId
) {
}

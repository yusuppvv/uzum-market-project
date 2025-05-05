package com.company.product.DTO;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductCart {
    private BigDecimal price;
    private int quantity;
}

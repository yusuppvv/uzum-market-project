package com.company.product.DTO;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProductCr {
    private String title;
    private String description;
    private BigDecimal price;
    private UUID categoryId;
    private UUID sellerId;
}

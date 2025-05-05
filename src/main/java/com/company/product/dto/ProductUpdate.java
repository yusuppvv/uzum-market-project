package com.company.product.DTO;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class ProductUpdate {

    private String title;
    private String description;
    private BigDecimal price;
    private UUID categoryId;
    private UUID sellerId;
}

package com.company.product.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductCr {
    private String title;
    private String description;
    private BigDecimal price;
    private UUID categoryId;
    private UUID sellerId;
}

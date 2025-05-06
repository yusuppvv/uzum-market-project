package com.company.product.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductUpdate {

    private String title;
    private String description;
    private BigDecimal price;
    private UUID categoryId;
    private UUID sellerId;
}

package com.company.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
public class ProductResponseDto {
    private UUID id;
    private String title;
    private String description;
    private double price;

    public ProductResponseDto(String title, String description, double price) {
        this.title = title;
        this.description = description;
        this.price = price;
    }
    public ProductResponseDto(UUID id, String title, String description, double price) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
    }
}

package com.company.product.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductCr {

    @NotBlank(message = "title must not blank!!!")
    @NotNull(message = "title must not null!!!")
    private String title;

    @NotBlank(message = "description must not blank!!!")
    @NotNull(message = "description must not null!!!")
    private String description;

    @NotBlank(message = "price must not blank!!!")
    @NotNull(message = "price must not null!!!")
    private BigDecimal price;

    @NotBlank(message = "categoryId must not blank!!!")
    @NotNull(message = "categoryId must not null!!!")
    private UUID categoryId;

    @NotBlank(message = "sellerId must not blank!!!")
    @NotNull(message = "sellerId must not null!!!")
    private UUID sellerId;
}

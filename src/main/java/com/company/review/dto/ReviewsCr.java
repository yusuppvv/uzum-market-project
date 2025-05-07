package com.company.review.dto;



import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewsCr {

    @NotBlank(message = "rating must not blank!!!")
    @NotNull(message = "rating must not null!!!")
    private Integer rating;

    @NotBlank(message = "comment must not blank!!!")
    @NotNull(message = "comment must not null!!!")
    private   String comment;

    @NotBlank(message = "productId must not blank!!!")
    @NotNull(message = "productId must not null!!!")
    private UUID productId;

    @NotBlank(message = "userId must not blank!!!")
    @NotNull(message = "userId must not null!!!")
    private UUID userId;

}

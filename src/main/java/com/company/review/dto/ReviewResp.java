package com.company.review.dto;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ReviewResp(
        Integer rating,
        String comment,
        UUID productId,
        UUID userId
) {
}

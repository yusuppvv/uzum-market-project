package com.company.review.DTO;


import java.util.UUID;

public record ReviewResp(
        Integer rating,
        String comment,
        UUID productId,
        UUID userId
) {
}

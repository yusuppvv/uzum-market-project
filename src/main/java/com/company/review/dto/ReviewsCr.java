package com.company.review.DTO;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;
@Data
@Builder
public class ReviewsCr {
    private Integer rating;
    private   String comment;
    private UUID productId;
    private UUID userId;

}

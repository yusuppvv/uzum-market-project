package com.company.review.dto;



import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewsCr {
    private Integer rating;
    private   String comment;
    private UUID productId;
    private UUID userId;

}

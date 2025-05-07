package com.company.review.dto;



import com.company.component.Components;
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

    @NotBlank(message = Components.NOT_BLANK)
    @NotNull(message = Components.NOT_NULL)
    private Integer rating;

    @NotBlank(message = Components.NOT_BLANK)
    @NotNull(message = Components.NOT_NULL)
    private   String comment;

    @NotBlank(message = Components.NOT_BLANK)
    @NotNull(message = Components.NOT_NULL)
    private UUID productId;

    @NotBlank(message = Components.NOT_BLANK)
    @NotNull(message = Components.NOT_NULL)
    private UUID userId;

}

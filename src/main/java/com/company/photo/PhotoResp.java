package com.company.photo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PhotoResp(
        UUID id,
        String name,
        UUID productId
) {
}

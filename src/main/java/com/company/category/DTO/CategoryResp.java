package com.company.category.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CategoryResp {
    private UUID id;
    private String name;

    public CategoryResp(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}

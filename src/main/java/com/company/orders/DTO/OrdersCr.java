package com.company.orders.DTO;

import com.company.component.Components;
import com.company.orders.Status;
import com.company.orders.Type;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrdersCr {
    @NotBlank(message = Components.NOT_BLANK)
    @NotNull(message = Components.NOT_NULL)
    private UUID userId;
    @NotBlank(message = Components.NOT_BLANK)
    @NotNull(message = Components.NOT_NULL)
    private List<UUID> cartIds;
}

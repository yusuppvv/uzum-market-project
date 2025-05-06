package com.company.orders.DTO;

import com.company.orders.Status;
import com.company.orders.Type;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderUpdate {
    private Type type;
    private Status status;
    private List<UUID> cartIds;
}

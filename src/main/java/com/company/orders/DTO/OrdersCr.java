package com.company.orders.DTO;

import com.company.orders.Status;
import com.company.orders.Type;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class OrdersCr {
    private UUID userId;
    private List<UUID> cartIds;
}

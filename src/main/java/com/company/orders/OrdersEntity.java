package com.company.orders;

import com.company.component.BaseMapper;
import com.company.users.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "orders")
public class OrdersEntity extends BaseMapper {
    @Column(nullable = false)
    private BigDecimal totalPrice;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

}

package com.company.cart;

import com.company.component.BaseMapper;
import com.company.orders.OrdersEntity;
import com.company.product.ProductEntity;
import com.company.users.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "cart")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "product_id"})
})
public class CartEntity extends BaseMapper {
    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "user_id", nullable = false)
    private UUID userId;


    @Column(name = "product_id", nullable = false)
    private UUID productId;
}

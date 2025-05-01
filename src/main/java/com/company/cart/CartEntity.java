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
public class CartEntity extends BaseMapper {

    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",
            insertable = false,
            updatable = false)
    private UserEntity user;

    @Column(name = "user_id")
    private UUID userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id",
            insertable = false,
            updatable = false)
    private OrdersEntity orders;

    @Column(name = "order_id")
    private UUID orderId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",
            insertable = false,
            updatable = false)
    private ProductEntity product;

    @Column(name = "product_id")
    private UUID productId;

    public CartEntity(Integer quantity, UUID userId, UUID orderId, UUID productId) {
        this.quantity = quantity;
        this.userId = userId;
        this.orderId = orderId;
        this.productId = productId;
    }
}

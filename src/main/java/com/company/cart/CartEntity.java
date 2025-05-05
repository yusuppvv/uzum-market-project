package com.company.cart;

import com.company.component.BaseMapper;
import com.company.orders.OrdersEntity;
import com.company.product.ProductEntity;
import com.company.users.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity(name = "cart")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartEntity extends BaseMapper {
    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",
            insertable = false,
            updatable = false)
    private UserEntity user;

    @Column(name = "user_id" , nullable = false)
    private UUID userId;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",
            insertable = false,
            updatable = false)
    private ProductEntity product;

    @Column(name = "product_id" , nullable = false)
    private UUID productId;
}

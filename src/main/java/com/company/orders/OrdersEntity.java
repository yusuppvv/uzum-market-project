package com.company.orders;

import com.company.cart.CartEntity;
import com.company.component.BaseMapper;
import com.company.users.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "orders")
public class OrdersEntity extends BaseMapper {

    private double totalPrice;
    private Status status;
    private Type type;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",
            insertable = false,
            updatable = false)
    private UserEntity user;

    @Column(name = "user_id")
    private UUID userId;

    @OneToMany(mappedBy = "orders")
    private List<CartEntity> carts;
}

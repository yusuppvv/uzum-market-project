package com.company.users;

import com.company.cart.CartEntity;
import com.company.component.BaseMapper;
import com.company.delivery.DeliveryEntity;
import com.company.orders.OrdersEntity;
import com.company.product.ProductEntity;
import com.company.review.ReviewEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserEntity extends BaseMapper {

    @Column(nullable = false)
    private String fullName;
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @Column(nullable = false )
    @Enumerated(value = EnumType.STRING)
    private Role role;


}

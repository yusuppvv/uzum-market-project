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

    public UserEntity(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<DeliveryEntity> delivery;

    @OneToMany(mappedBy = "user")
    private List<OrdersEntity> orders;

    @OneToMany(mappedBy = "user")
    private List<ProductEntity> products;

    @OneToMany(mappedBy = "user")
    private List<CartEntity> carts;

    @OneToMany(mappedBy = "user")
    private List<ReviewEntity> reviews;
}

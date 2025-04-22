package com.company.order;

import com.company.cart.Cart;
import com.company.user.User;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user;

    private BigDecimal totalPrice;
    private String status;
    private String type;

    @OneToMany(mappedBy = "order")
    private List<Cart> cartItems;
}

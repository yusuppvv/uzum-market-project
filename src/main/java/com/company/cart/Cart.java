package com.company.cart;

import com.company.order.Order;
import com.company.product.Product;
import com.company.user.User;
import jakarta.persistence.*;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Product product;

    @ManyToOne
    private User user;

    private Integer quantity;

    @ManyToOne
    private Order order;
}

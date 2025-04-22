package com.company.review;

import com.company.product.Product;
import com.company.user.User;
import jakarta.persistence.*;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private short rating;
    private String comment;

    @ManyToOne
    private Product product;

    @ManyToOne
    private User user;
}

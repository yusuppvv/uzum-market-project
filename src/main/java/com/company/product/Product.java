package com.company.product;

import com.company.cart.Cart;
import com.company.category.Category;
import com.company.photo.Photo;
import com.company.review.Review;
import com.company.user.User;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User seller;

    @OneToMany(mappedBy = "product")
    private List<Photo> photos;

    @OneToMany(mappedBy = "product")
    private List<Review> reviews;

    @OneToMany(mappedBy = "product")
    private List<Cart> cartItems;
}

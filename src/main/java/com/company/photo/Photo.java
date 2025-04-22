package com.company.photo;

import com.company.product.Product;
import jakarta.persistence.*;

@Entity
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private byte[] data;

    @ManyToOne
    private Product product;
}

package com.company.product;

import com.company.photo.PhotoEntity;
import com.company.cart.CartEntity;
import com.company.category.CategoryEntity;
import com.company.component.BaseMapper;
import com.company.review.ReviewEntity;
import com.company.users.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "product")
public class ProductEntity extends BaseMapper {

    //nullable false
    private String title;
    private String description;
    private double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id",
            insertable = false,
            updatable = false)
    private UserEntity user;

    @Column(name = "seller_id")
    private UUID userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id",
            insertable = false,
            updatable = false)
    private CategoryEntity category;

    @Column(name = "category_id")
    private UUID categoryId;

    @OneToMany(mappedBy = "product")
    private List<PhotoEntity> photos;

    @OneToOne(mappedBy = "product")
    private CartEntity cart;

    @OneToMany(mappedBy = "product")
    private List<ReviewEntity> reviews;

    public ProductEntity(String title, String description, double price) {
        this.title = title;
        this.description = description;
        this.price = price;
    }

}

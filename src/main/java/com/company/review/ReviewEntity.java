package com.company.review;

import com.company.component.BaseMapper;
import com.company.product.ProductEntity;
import com.company.users.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

import java.util.UUID;

@Entity
@Table(name = "review")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Check(constraints = "rating BETWEEN 1 AND 5")
public class ReviewEntity extends BaseMapper {


    private int rating;
    private String comment;

    public ReviewEntity(int rating, String comment, UUID productId, UUID userId) {
        this.rating = rating;
        this.comment = comment;
        this.productId = productId;
        this.userId = userId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",
            insertable = false,
            updatable = false)
    private ProductEntity product;

    @Column(name = "product_id")
    private UUID productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",
            insertable = false,
            updatable = false)
    private UserEntity user;

    @Column(name = "user_id")
    private UUID userId;
}

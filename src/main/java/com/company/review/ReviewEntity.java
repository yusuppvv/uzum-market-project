package com.company.review;

import com.company.component.BaseMapper;
import com.company.product.ProductEntity;
import com.company.users.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity(name = "review")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Check(constraints = "rating BETWEEN 1 AND 5")
public class ReviewEntity extends BaseMapper {

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false)
    private String comment;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id",
            insertable = false,
            updatable = false)
    private ProductEntity product;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",
            insertable = false,
            updatable = false)
    private UserEntity user;

    @Column(name = "user_id", nullable = false)
    private UUID userId;
}

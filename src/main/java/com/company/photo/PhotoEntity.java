package com.company.photo;

import com.company.component.BaseMapper;
import com.company.product.ProductEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "photo")
public class PhotoEntity extends BaseMapper {

    private byte[] data;
    @Column
    private String name;


    @Column(name = "product_id")
    private UUID productId;
}

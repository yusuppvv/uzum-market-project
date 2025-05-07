package com.company.category;

import com.company.component.BaseMapper;
import com.company.product.ProductEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity(name = "category")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryEntity extends BaseMapper {
    @Column(nullable = false)
    private String name;

}

package com.company.product;

import com.company.component.ApiResponse;
import com.company.component.Components;
import com.company.exception.classes.ItemNotFoundException;
import com.company.product.DTO.ProductCr;
import com.company.product.DTO.ProductResp;
import com.company.product.DTO.ProductUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ApiResponse<ProductResp> create(ProductCr productCr) {

        ProductEntity saved = productRepository.save(ProductEntity
                .builder()
                .title(productCr.getTitle())
                .description(productCr.getDescription())
                .price(productCr.getPrice())
                .categoryId(productCr.getCategoryId())
                .sellerId(productCr.getSellerId())
                .build());

        return new ApiResponse<>(toDto(saved));
    }

    public ApiResponse<ProductResp> getById(UUID id) {
        return new ApiResponse<>(toDto(findProductById(id)));
    }

    public ApiResponse<Page<ProductResp>> getAll(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Components.CREATED_AT));
        List<ProductResp> list = productRepository.findAllByVisibilityTrue(pageable)
                .stream()
                .map(this::toDto)
                .toList();

        return new ApiResponse<>(new PageImpl<>(list, pageable, list.size()));
    }

    public ApiResponse<ProductResp> update(UUID id, ProductUpdate productUpdate) {
        ProductEntity productEntity = findProductById(id);
        productEntity.setDescription(productUpdate.getDescription());
        productEntity.setTitle(productUpdate.getTitle());
        productEntity.setPrice(productUpdate.getPrice());
        productEntity.setCategoryId(productUpdate.getCategoryId());
        productEntity.setSellerId(productUpdate.getSellerId());

        return new ApiResponse<>(toDto(productRepository.save(productEntity)));
    }

    public ApiResponse<String> delete(UUID id) {
        ProductEntity productEntity = findProductById(id);
        productEntity.setVisibility(false);

        productRepository.save(productEntity);
        return new ApiResponse<>(Components.DELETED);
    }

    public ApiResponse<Page<ProductResp>> getAllByCategoryId(UUID categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Components.CREATED_AT));

        List<ProductResp> list = productRepository.findByCategoryIdAndVisibilityTrue(categoryId , pageable)
                .stream()
                .map(this::toDto)
                .toList();

        return new ApiResponse<>(new PageImpl<>(list, pageable, list.size()));
    }

    public ApiResponse<Page<ProductResp>> getAllBySellerId(UUID sellerId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Components.CREATED_AT));

        List<ProductResp> list = productRepository.findBySellerIdAndVisibilityTrue(sellerId , pageable)
                .stream()
                .map(this::toDto)
                .toList();

        return new ApiResponse<>(new PageImpl<>(list, pageable, list.size()));
    }

    private ProductEntity findProductById(UUID id) {
        return productRepository
                .findByIdAndVisibilityTrue(id)
                .orElseThrow(ItemNotFoundException::new);
    }
    private ProductResp toDto(ProductEntity productEntity) {
        return ProductResp
                .builder()
                .id(productEntity.getId())
                .title(productEntity.getTitle())
                .description(productEntity.getDescription())
                .price(productEntity.getPrice())
                .categoryId(productEntity.getCategoryId())
                .sellerId(productEntity.getSellerId())
                .build();
    }

    public ApiResponse<Page<ProductResp>> getAllByPriceRange(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Components.CREATED_AT));
        List<ProductResp> list = productRepository.findAllByOrderByPriceAsc(pageable)
                .stream()
                .map(this::toDto)
                .toList();

        return new ApiResponse<>(new PageImpl<>(list, pageable, list.size()));
    }
}

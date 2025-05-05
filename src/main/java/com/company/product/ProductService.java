package com.company.product;

import com.company.exception.ItemNotFoundException;
import com.company.product.DTO.ProductCr;
import com.company.product.DTO.ProductResp;
import com.company.product.DTO.ProductUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResp create(ProductCr productCr) {

        ProductEntity saved = productRepository.save(ProductEntity
                .builder()
                .title(productCr.getTitle())
                .description(productCr.getDescription())
                .price(productCr.getPrice())
                .categoryId(productCr.getCategoryId())
                .sellerId(productCr.getSellerId())
                .build());

        return toDto(saved);
    }

    public ProductResp getById(UUID id) {
        return toDto(findProductById(id));
    }

    public Page<ProductResp> getAll(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt"));
        List<ProductResp> list = productRepository.findAllByVisibilityTrue(pageable)
                .stream()
                .map(this::toDto)
                .toList();

        return new PageImpl<>(list, pageable, list.size());
    }

    public ProductResp update(UUID id, ProductUpdate productUpdate) {
        ProductEntity productEntity = findProductById(id);
        productEntity.setDescription(productUpdate.getDescription());
        productEntity.setTitle(productUpdate.getTitle());
        productEntity.setPrice(productUpdate.getPrice());
        productEntity.setCategoryId(productUpdate.getCategoryId());
        productEntity.setSellerId(productUpdate.getSellerId());

        return toDto(productRepository.save(productEntity));
    }

    public String delete(UUID id) {
        ProductEntity productEntity = findProductById(id);
        productEntity.setVisibility(false);

        productRepository.save(productEntity);
        return "Deleted";
    }

    public Page<ProductResp> getAllByCategoryId(UUID categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt"));

        List<ProductResp> list = productRepository.findByCategoryIdAndVisibilityTrue(categoryId , pageable)
                .stream()
                .map(this::toDto)
                .toList();

        return new PageImpl<>(list, pageable, list.size());
    }

    public Page<ProductResp> getAllBySellerId(UUID sellerId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt"));

        List<ProductResp> list = productRepository.findBySellerIdAndVisibilityTrue(sellerId , pageable)
                .stream()
                .map(this::toDto)
                .toList();

        return new PageImpl<>(list, pageable, list.size());
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

    public Page<ProductResp> getAllByPriceRange(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt"));
        List<ProductResp> list = productRepository.findAllByOrderByPriceAsc(pageable)
                .stream()
                .map(this::toDto)
                .toList();

        return new PageImpl<>(list, pageable, list.size());
    }
}

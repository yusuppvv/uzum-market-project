package com.company.product;

import com.company.exception.AppBadRequestException;
import com.company.product.dto.ProductCreationDto;
import com.company.product.dto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponseDto createProduct(ProductCreationDto product) {
        Optional<ProductEntity> optionalProduct = productRepository.findByTitle(product.getTitle());
        if (optionalProduct.isPresent()) throw new AppBadRequestException("Product already exists");
        ProductEntity save = productRepository.save(new ProductEntity(product.getTitle(), product.getDescription(),
                product.getPrice()));
        return ProductResponseDto.builder()
                .id(save.getId())
                .title(save.getTitle())
                .price(save.getPrice())
                .description(save.getDescription())
                .build();
    }

    public List<ProductResponseDto> getAllProduct() {
        List<ProductEntity> all = productRepository.findAll();
        return all.stream().map(product -> ProductResponseDto.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .build()).collect(toList());
    }


    public ProductResponseDto updateProduct(UUID id, ProductCreationDto product) {
        Optional<ProductEntity> byIdIsVisibilityTrue = productRepository.findByIdAndVisibilityTrue(id);
        if (byIdIsVisibilityTrue.isPresent()) {
            ProductEntity productEntity = byIdIsVisibilityTrue.get();
            productEntity.setId(byIdIsVisibilityTrue.get().getId());
            productEntity.setTitle(product.getTitle());
            productEntity.setDescription(product.getDescription());
            productEntity.setPrice(product.getPrice());
            ProductEntity save = productRepository.save(productEntity);
            return new ProductResponseDto(save.getId(), save.getTitle(), save.getDescription(), save.getPrice());
        } else {
            throw new AppBadRequestException("Product not found");
        }
    }

    public String deleteById(UUID id) {
        Optional<ProductEntity> byId = productRepository.findById(id);
        if (byId.isPresent()) {
            byId.get().setVisibility(false);
            productRepository.save(byId.get());
            return "Product deleted successfully";
        } else {
            return "Product not found";
        }
    }
}

package com.company.product;

import com.company.category.CategoryEntity;
import com.company.category.DTO.CategoryResp;
import com.company.exception.AppBadRequestException;
import com.company.product.dto.ProductCreationDto;
import com.company.product.dto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Page<ProductResponseDto> getAllProduct(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
//        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt"));

        List<ProductResponseDto> list = productRepository
                .findAllByVisibilityTrue(pageable)
                .stream()
                .map(this::toDTO)
                .toList();

        return new PageImpl<>(list, pageable, list.size());

    }
    public Page<ProductResponseDto> getBySellerId(UUID sellerId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        List<ProductResponseDto> list = productRepository.findBySellerIdAndVisibilityTrue(sellerId, pageable).stream().map(this::toDTO).toList();
        return new PageImpl<>(list, pageable, list.size());
    }

    public Page<ProductResponseDto> searchByTitle(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<ProductResponseDto> list = productRepository.findAllByTitleContainingIgnoreCaseAndVisibilityTrue(title, true, pageable)
                .stream().map(this::toDTO).toList();
        return new PageImpl<>(list, pageable, list.size());
    }

    public Page<ProductResponseDto> getByRange(double minPrice, double maxPrice, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<ProductResponseDto> list = productRepository.findAllByPriceBetweenAndVisibilityTrue(minPrice, maxPrice, true, pageable)
                .stream().map(this::toDTO).toList();
        return new PageImpl<>(list, pageable, list.size());
    }


    private ProductResponseDto toDTO(ProductEntity productEntity) {
        return ProductResponseDto
                .builder()
                .id(productEntity.getId())
                .title(productEntity.getTitle())
                .description(productEntity.getDescription())
                .price(productEntity.getPrice())
                .build();
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

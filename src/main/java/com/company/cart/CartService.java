package com.company.cart;

import com.company.cart.DTO.CartCr;
import com.company.cart.DTO.CartResp;
import com.company.component.ApiResponse;
import com.company.exception.ItemNotFoundException;
import com.company.product.DTO.ProductCart;
import com.company.product.DTO.ProductResp;
import com.company.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;

    public ApiResponse<CartResp> create(CartCr cartCr) {
        CartEntity cartEntity = CartEntity
                .builder()
                .quantity(cartCr.getQuantity())
                .userId(cartCr.getUserId())
                .productId(cartCr.getProductId())
                .build();

        return new ApiResponse<>(toDto(cartRepository.save(cartEntity)));
    }

    private CartResp toDto(CartEntity save) {
        return new CartResp(save.getId(), save.getQuantity(), save.getUserId(), save.getProductId());
    }

    public ApiResponse<CartResp> getById(UUID id) {
        return new ApiResponse<>(toDto(cartRepository.findByIdAndVisibilityTrue(id).orElseThrow(ItemNotFoundException::new)));
    }

    public ApiResponse<Page<CartResp>> getAll(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt"));

        List<CartResp> list = cartRepository.findAllByVisibilityTrue(pageable)
                .stream()
                .map(this::toDto)
                .toList();

        return new ApiResponse<>(new PageImpl<>(list, pageable, list.size()));
    }

    public ApiResponse<Page<CartResp>> getByUserId(UUID id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt"));

        List<CartResp> list = cartRepository.findAllByUserIdAndVisibilityTrue(id, pageable)
                .stream()
                .map(this::toDto)
                .toList();
        return new ApiResponse<>(new PageImpl<>(list, pageable, list.size()));
    }

    public ApiResponse<CartResp> update(UUID id, CartCr cartCr) {
        CartEntity cartEntity = cartRepository.findByIdAndVisibilityTrue(id).orElseThrow(ItemNotFoundException::new);

        cartEntity.setQuantity(cartCr.getQuantity());

        return new ApiResponse<>(toDto(cartRepository.save(cartEntity)));
    }

    public ApiResponse<String> delete(UUID id) {
        CartEntity cartEntity = cartRepository.findByIdAndVisibilityTrue(id).orElseThrow(ItemNotFoundException::new);

        cartEntity.setVisibility(false);

        cartRepository.save(cartEntity);
        return new ApiResponse<>("Success");
    }

    public ProductCart getByCartIdReturnProduct(UUID cartId, UUID userId) {
        CartEntity cartEntity = cartRepository
                .findByIdAndUserIdAndVisibilityTrue(cartId, userId)
                .orElseThrow(ItemNotFoundException::new);

        ProductResp productResp = productService
                .getById(cartEntity.getProductId());

        return ProductCart.builder()
                .price(productResp.getPrice())
                .quantity(cartEntity.getQuantity())
                .build();
    }

}

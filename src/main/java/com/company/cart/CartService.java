package com.company.cart;

import com.company.cart.dto.CartCreation;
import com.company.cart.dto.CartResponse;
import com.company.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    public CartResponse add(CartCreation creation) {
        Optional<CartEntity> optionalCart = cartRepository.findByOrderIdAndVisibilityTrue(creation.getOrderId());
        if (optionalCart.isPresent()) {
            throw new BadRequestException("Cart already exists.");
        }
        else {
            CartEntity saved = cartRepository.save(toEntity(creation));
            return toDto(saved);
        }
    }

    public List<CartResponse> get() {
        List<CartEntity> list = cartRepository.findAllByVisibilityTrue();
        if (list.isEmpty()) {
            throw new BadRequestException("Cart is empty.");
        }
        return list.stream()
                .map(this::toDto)
                .toList();
    }

    public CartResponse update(CartCreation creation) {
        Optional<CartEntity> optional = cartRepository.findByUserIdAndVisibilityTrue(creation.getUserId());
        if (optional.isPresent()) {
            CartEntity cartEntity = optional.get();
            cartEntity.setOrderId(creation.getOrderId());
            cartEntity.setProductId(creation.getProductId());
            cartEntity.setQuantity(creation.getQuantity());
            CartEntity saved = cartRepository.save(cartEntity);
            return toDto(saved);
        }
        else {
            throw new BadRequestException("Cart not found.");
        }

    }

    public String delete(UUID id) {
        Optional<CartEntity> optional = cartRepository.findByIdAndVisibilityTrue(id);
        if (optional.isPresent()) {
            optional.get().setVisibility(false);
            cartRepository.save(optional.get());
            return "Cart deleted successfully.";
        }
        else {
            throw new BadRequestException("Cart not found.");
        }
    }

    private CartResponse toDto(CartEntity saved) {
        return new CartResponse(saved.getQuantity(), saved.getUserId(), saved.getOrderId(), saved.getProductId());
    }

    private CartEntity toEntity(CartCreation creation) {
        return new CartEntity(creation.getQuantity(), creation.getUserId(), creation.getOrderId() ,creation.getProductId());
    }
}

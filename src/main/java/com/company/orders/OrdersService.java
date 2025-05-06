package com.company.orders;

import com.company.cart.CartService;
import com.company.component.ApiResponse;
import com.company.exception.ItemNotFoundException;
import com.company.orders.DTO.OrderUpdate;
import com.company.orders.DTO.OrdersCr;
import com.company.orders.DTO.OrdersResp;
import com.company.product.DTO.ProductCart;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static com.company.orders.Status.UNPAYED;
import static com.company.orders.Type.CART;
import static com.company.orders.Type.valueOf;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final CartService cartService;

    public ApiResponse<OrdersResp> create(OrdersCr ordersCr) {

        BigDecimal total = BigDecimal.ZERO;

        for (UUID cartId : ordersCr.getCartIds()) {

            ProductCart productCart = cartService
                    .getByCartIdReturnProduct(cartId,ordersCr.getUserId());

            //5000
            //10

            BigDecimal itemTotal = productCart
                    .getPrice()
                    .multiply(BigDecimal.valueOf(productCart.getQuantity()));
            total = total.add(itemTotal);
        }

        OrdersEntity ordersEntity = OrdersEntity
                .builder()
                .totalPrice(total)
                .status(UNPAYED)
                .type(CART)
                .userId(ordersCr.getUserId())
                .build();

        return new ApiResponse<>(toDto(ordersRepository.save(ordersEntity)));
    }

    private OrdersResp toDto(OrdersEntity orders) {
        return new OrdersResp(orders.getId(),orders.getTotalPrice(), orders.getStatus(), orders.getType(), orders.getUserId());
    }

    public ApiResponse<OrdersResp> getById(UUID id) {
        return new ApiResponse<>(toDto(ordersRepository.findByIdAndVisibilityTrue(id).orElseThrow(ItemNotFoundException::new)));
    }

    public ApiResponse<Page<OrdersResp>> getAll(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt"));

        List<OrdersResp> list = ordersRepository.findAllByVisibilityTrue(pageable).stream()
                .map(this::toDto)
                .toList();

        return new ApiResponse<>(new PageImpl<>(list, pageable, list.size()));
    }

    public ApiResponse<Page<OrdersResp>> getByUserId(UUID userId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt"));

        List<OrdersResp> list = ordersRepository.findAllByUserIdAndVisibilityTrue(userId,pageable).stream()
                .map(this::toDto)
                .toList();

        return new ApiResponse<>(new PageImpl<>(list, pageable, list.size()));
    }

    public ApiResponse<OrdersResp> update(UUID id, OrderUpdate orderUpdate) {
        OrdersEntity ordersEntity = ordersRepository.
                findByIdAndVisibilityTrue(id)
                .orElseThrow(ItemNotFoundException::new);

        BigDecimal total = BigDecimal.ZERO;

        for (UUID cartId : orderUpdate.getCartIds()) {
            ProductCart productCart = cartService.getByCartIdReturnProduct(cartId ,ordersEntity.getUserId());

            BigDecimal itemTotal = productCart
                    .getPrice()
                    .multiply(BigDecimal.valueOf(productCart.getQuantity()));
            total = total.add(itemTotal);
        }

        ordersEntity.setType(orderUpdate.getType());
        ordersEntity.setStatus(orderUpdate.getStatus());

        return new ApiResponse<>(toDto(ordersRepository.save(ordersEntity)));
    }

    public ApiResponse<String> delete(UUID id) {
        OrdersEntity ordersEntity = ordersRepository.findByIdAndVisibilityTrue(id).orElseThrow(ItemNotFoundException::new);

        ordersEntity.setVisibility(false);

        ordersRepository.save(ordersEntity);
        return new ApiResponse<>("Deleted");
    }

    public ApiResponse<OrdersResp> update(UUID id, Type type) {

        OrdersEntity ordersEntity = ordersRepository
                .findByIdAndVisibilityTrue(id)
                .orElseThrow();

        ordersEntity.setType(type);
        ordersEntity.setStatus(Status.PAYED);

        ordersRepository.save(ordersEntity);

        return new ApiResponse<>(toDto(ordersEntity));
    }
}

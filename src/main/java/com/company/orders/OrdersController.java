package com.company.orders;

import com.company.orders.DTO.OrderUpdate;
import com.company.orders.DTO.OrdersCr;
import com.company.orders.DTO.OrdersResp;
import com.company.review.DTO.ReviewsCr;
import com.company.review.DTO.ReviewResp;
import com.company.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService ordersService;

    @PostMapping
    public ResponseEntity<OrdersResp> create(@RequestBody OrdersCr ordersCr) {
        return ResponseEntity.ok(ordersService.create(ordersCr));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdersResp> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(ordersService.getById(id));
    }

    @GetMapping("/get-all")
    public ResponseEntity<Page<OrdersResp>> getAll(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ordersService.getAll(page , size));
    }

    @GetMapping("get-by-user/{userId}")
    public ResponseEntity<Page<OrdersResp>> getByUserId(@PathVariable UUID userId,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ordersService.getByUserId(userId, page , size));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrdersResp> update(@PathVariable UUID id,
                                             @RequestBody OrderUpdate orderUpdate) {
        return ResponseEntity.ok(ordersService.update(id, orderUpdate));
    }

    @PatchMapping("/pay")
    public ResponseEntity<OrdersResp> update(@RequestParam UUID id,
                                             @RequestParam Type type) {
        return ResponseEntity.ok(ordersService.update(id, type));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(ordersService.delete(id));
    }
}

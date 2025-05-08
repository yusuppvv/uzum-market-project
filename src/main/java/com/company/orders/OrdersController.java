package com.company.orders;

import com.company.component.ApiResponse;
import com.company.orders.DTO.OrderUpdate;
import com.company.orders.DTO.OrdersCr;
import com.company.orders.DTO.OrdersResp;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrdersController {

    private final OrdersService ordersService;

    @PostMapping
    @PermitAll
    public ResponseEntity<ApiResponse<OrdersResp>> create(@RequestBody OrdersCr ordersCr) {
        return ResponseEntity.ok(ordersService.create(ordersCr));
    }

    @GetMapping("/{id}")
    @PermitAll
    public ResponseEntity<ApiResponse<OrdersResp>> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(ordersService.getById(id));
    }

    @GetMapping("/get-all")
    @PermitAll
    public ResponseEntity<ApiResponse<Page<OrdersResp>>> getAll(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ordersService.getAll(page , size));
    }

    @GetMapping("/get-by-user/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<ApiResponse<Page<OrdersResp>>> getByUserId(@PathVariable UUID userId,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ordersService.getByUserId(userId, page , size));
    }

    @PutMapping("/{id}")
    @PermitAll
    public ResponseEntity<ApiResponse<OrdersResp>> update(@PathVariable UUID id,
                                             @RequestBody OrderUpdate orderUpdate) {
        return ResponseEntity.ok(ordersService.update(id, orderUpdate));
    }

    @PatchMapping("/pay")
    @PermitAll
    public ResponseEntity<ApiResponse<OrdersResp>> update(@RequestParam UUID id,
                                             @RequestParam Type type) {
        return ResponseEntity.ok(ordersService.update(id, type));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(ordersService.delete(id));
    }
}

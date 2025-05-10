package com.company.cart;

import com.company.cart.DTO.CartCr;
import com.company.cart.DTO.CartResp;
import com.company.component.ApiResponse;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping
    @PermitAll
    public ResponseEntity<ApiResponse<CartResp>> create(@RequestBody CartCr cartCr) {
        return ResponseEntity.ok(cartService.create(cartCr));
    }

    @GetMapping("/get-by-id/{id}")
    @PermitAll
    public ResponseEntity<ApiResponse<CartResp>> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(cartService.getById(id));
    }

    @GetMapping("/get-all")
    @PermitAll
    public ResponseEntity<ApiResponse<Page<CartResp>>> getAll(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(cartService.getAll(page , size));
    }

    @GetMapping("/get-by-user/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public ResponseEntity<ApiResponse<Page<CartResp>>> getByUser(@PathVariable UUID id,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(cartService.getByUserId(id,page,size));
    }

    @PutMapping("/{id}")
    @PermitAll
    public ResponseEntity<ApiResponse<CartResp>> update(@PathVariable UUID id, @RequestBody CartCr cartCr) {
        return ResponseEntity.ok(cartService.update(id, cartCr));
    }

    @DeleteMapping("/delete/{id}")
    @PermitAll
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(cartService.delete(id));
    }
}

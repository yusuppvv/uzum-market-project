package com.company.cart;

import com.company.cart.DTO.CartCr;
import com.company.cart.DTO.CartResp;
import com.company.component.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<ApiResponse<CartResp>> create(@RequestBody CartCr cartCr) {
        return ResponseEntity.ok(cartService.create(cartCr));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CartResp>> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(cartService.getById(id));
    }

    @GetMapping("/get-all")
    public ResponseEntity<ApiResponse<Page<CartResp>>> getAll(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(cartService.getAll(page , size));
    }

    @GetMapping("/get-by-user/{id}")
    public ResponseEntity<ApiResponse<Page<CartResp>>> getByUser(@PathVariable UUID id,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(cartService.getByUserId(id,page,size));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CartResp>> update(@PathVariable UUID id, @RequestBody CartCr cartCr) {
        return ResponseEntity.ok(cartService.update(id, cartCr));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(cartService.delete(id));
    }
}

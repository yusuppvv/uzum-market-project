package com.company.cart;

import com.company.cart.dto.CartCreation;
import com.company.cart.dto.CartResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<CartResponse> add(@RequestBody CartCreation cartCreation) {
        return ResponseEntity.ok(cartService.add(cartCreation));
    }

    @GetMapping("/get")
    public ResponseEntity<List<CartResponse>> get() {
        return ResponseEntity.ok(cartService.get());
    }

    @PutMapping("/update")
    public ResponseEntity<CartResponse> update(@RequestBody CartCreation creation) {
        return ResponseEntity.ok(cartService.update(creation));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(UUID id) {
        return ResponseEntity.ok(cartService.delete(id));
    }
}

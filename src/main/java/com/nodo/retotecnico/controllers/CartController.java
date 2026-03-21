package com.nodo.retotecnico.controllers;

import com.nodo.retotecnico.Models.Buys;
import com.nodo.retotecnico.Services.CartService;
import com.nodo.retotecnico.dto.CartRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/{email}")
    public ResponseEntity<List<Buys>> getCart(@PathVariable String email) {
        return ResponseEntity.ok(cartService.getCartByEmail(email));
    }

    @PostMapping
    public ResponseEntity<?> addToCart(@RequestBody CartRequest request) {
        try {
            Buys result = cartService.addToCart(request);
            return ResponseEntity.ok(result);
        } 
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
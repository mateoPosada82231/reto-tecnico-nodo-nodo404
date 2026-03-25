package com.nodo.retotecnico.controllers;

import com.nodo.retotecnico.models.CartItem;
import com.nodo.retotecnico.services.CartService;
import com.nodo.retotecnico.dto.CartRequest;
import com.nodo.retotecnico.dto.CartSummaryResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // 🔵 VER CARRITO
    @GetMapping("/{email}")
    public ResponseEntity<CartSummaryResponse> getCart(@PathVariable String email, Authentication authentication) {
        enforceOwner(email, authentication);
        return ResponseEntity.ok(cartService.getCartByEmail(email));
    }

    // 🟢 AGREGAR AL CARRITO
    @PostMapping
    public ResponseEntity<?> addToCart(@RequestBody CartRequest request, Authentication authentication) {
        enforceOwner(request.getEmail(), authentication);
        try {
            CartItem result = cartService.addToCart(request);
            return ResponseEntity.ok(result);
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ELIMINAR UN PRODUCTO
    @DeleteMapping("/item/{cartItemId}")
    public ResponseEntity<String> deleteItem(
            @PathVariable Integer cartItemId,
            @RequestParam String email,
            Authentication authentication) {

        enforceOwner(email, authentication);

        cartService.deleteItem(cartItemId, email);
        return ResponseEntity.ok("Producto eliminado del carrito");
    }

    // LIMPIAR TODO EL CARRITO
    @DeleteMapping("/clear/{email}")
    public ResponseEntity<String> clearCart(@PathVariable String email, Authentication authentication) {

        enforceOwner(email, authentication);

        cartService.clearCart(email);
        return ResponseEntity.ok("Carrito limpiado correctamente");
    }

    private void enforceOwner(String requestedEmail, Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication required");
        }
        if (requestedEmail == null || !authentication.getName().equalsIgnoreCase(requestedEmail)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No puedes operar sobre otra cuenta");
        }
    }
}

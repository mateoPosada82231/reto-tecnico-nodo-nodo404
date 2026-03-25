package com.nodo.retotecnico.controllers;

import com.nodo.retotecnico.models.Buys;
import com.nodo.retotecnico.services.BuysService;
import com.nodo.retotecnico.dto.BuyRequest;
import com.nodo.retotecnico.dto.DirectBuyRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/buys")
public class BuysController {

    private final BuysService buysService;

    public BuysController(BuysService buysService) {
        this.buysService = buysService;
    }

    @GetMapping
    public ResponseEntity<List<Buys>> getAllBuys() {
        return ResponseEntity.ok(buysService.getAllBuys());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Buys> getBuyById(@PathVariable Integer id) {
        return buysService.getBuyById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Buys> createBuy(@RequestBody BuyRequest buyRequest, Authentication authentication) {
        enforceOwner(buyRequest.getUserEmail(), authentication);
        Buys newBuy = buysService.createBuy(
                buyRequest.getUserEmail(),
                buyRequest.getExtensionId(),
                buyRequest.getPaymentMethod(),
                null,
                null
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(newBuy);
    }

    @PostMapping("/direct")
    public ResponseEntity<Buys> createDirectBuy(@RequestBody DirectBuyRequest buyRequest, Authentication authentication) {
        enforceOwner(buyRequest.getEmail(), authentication);
        if (isBlank(buyRequest.getLanguage()) || isBlank(buyRequest.getPlatform())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "language y platform son obligatorios");
        }
        Buys newBuy = buysService.createBuy(
                buyRequest.getEmail(),
                buyRequest.getExtensionId(),
                buyRequest.getPaymentMethod(),
                buyRequest.getLanguage(),
                buyRequest.getPlatform()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(newBuy);
    }

    @PostMapping("/checkout")
    public ResponseEntity<String> checkout(@RequestBody com.nodo.retotecnico.dto.BuyRequest request,
                                           Authentication authentication) {
        enforceOwner(request.getUserEmail(), authentication);
        buysService.checkout(request);
        return ResponseEntity.ok("Compra realizada con éxito y carrito vaciado.");
    }

    private void enforceOwner(String requestedEmail, Authentication authentication) {
        if (authentication == null || authentication.getName() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Authentication required");
        }
        if (requestedEmail == null || !authentication.getName().equalsIgnoreCase(requestedEmail)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No puedes operar sobre otra cuenta");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    // Nota: Normalmente las compras no se editan o borran por auditoría,
    // pero si tu compañero te lo pide, se agregan igual que en Extensions.
}
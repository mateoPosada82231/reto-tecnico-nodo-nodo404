package com.nodo.retotecnico.controllers;

import com.nodo.retotecnico.models.Buys;
import com.nodo.retotecnico.services.BuysService;
import com.nodo.retotecnico.dto.BuyRequest;
import com.nodo.retotecnico.dto.CheckoutSummaryResponse;
import com.nodo.retotecnico.dto.DirectBuyRequest;
import com.nodo.retotecnico.dto.DirectBuySummaryResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.math.BigDecimal;

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

    @GetMapping("/user/{email}")
    public ResponseEntity<List<Buys>> getBuysByUser(@PathVariable String email, Authentication authentication) {
        enforceOwner(email, authentication);
        return ResponseEntity.ok(buysService.getBuysByUserEmail(email));
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
    public ResponseEntity<DirectBuySummaryResponse> createDirectBuy(@RequestBody DirectBuyRequest buyRequest, Authentication authentication) {
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
        BigDecimal totalPrice = (newBuy.getExtension() != null && newBuy.getExtension().getPrice() != null)
                ? newBuy.getExtension().getPrice()
                : BigDecimal.ZERO;

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new DirectBuySummaryResponse(newBuy, totalPrice, "Compra directa realizada con exito")
        );
    }

    @PostMapping("/checkout")
    public ResponseEntity<CheckoutSummaryResponse> checkout(@RequestBody com.nodo.retotecnico.dto.BuyRequest request,
                                                            Authentication authentication) {
        enforceOwner(request.getUserEmail(), authentication);
        return ResponseEntity.ok(buysService.checkout(request));
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

}

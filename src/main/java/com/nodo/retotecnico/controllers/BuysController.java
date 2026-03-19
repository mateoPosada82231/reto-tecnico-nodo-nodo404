package com.nodo.retotecnico.controllers;

import com.nodo.retotecnico.models.Buys;
import com.nodo.retotecnico.services.BuysService;
import com.nodo.retotecnico.dto.BuyRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Buys> createBuy(@RequestBody BuyRequest buyRequest) {
        Buys newBuy = buysService.createBuy(
                buyRequest.getUserEmail(),
                buyRequest.getExtensionId(),
                buyRequest.getPaymentMethod()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(newBuy);
    }

    // Nota: Normalmente las compras no se editan o borran por auditoría,
    // pero si tu compañero te lo pide, se agregan igual que en Extensions.
}
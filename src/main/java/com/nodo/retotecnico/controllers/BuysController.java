package com.nodo.retotecnico.controllers;

import com.nodo.retotecnico.Models.Buys;
import com.nodo.retotecnico.Services.BuysService;
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
    public ResponseEntity<Buys> createBuy(
            @RequestParam String userEmail,
            @RequestParam Integer extensionId,
            @RequestParam String paymentMethod) {

        Buys newBuy = buysService.createBuy(userEmail, extensionId, paymentMethod);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBuy);
    }

    // Nota: Normalmente las compras no se editan o borran por auditoría,
    // pero si tu compañero te lo pide, se agregan igual que en Extensions.
}
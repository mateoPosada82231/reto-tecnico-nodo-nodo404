package com.nodo.retotecnico.controllers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.nodo.retotecnico.Models.Buys;
import com.nodo.retotecnico.Services.BuysService;

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

    @PostMapping
    public ResponseEntity<Buys> createBuy(
            @RequestParam String userEmail,
            @RequestParam Integer extensionId,
            @RequestParam String paymentMethod) {


        Buys newBuy = buysService.createBuy(userEmail, extensionId, paymentMethod);

        return ResponseEntity.status(HttpStatus.CREATED).body(newBuy);
    }
}
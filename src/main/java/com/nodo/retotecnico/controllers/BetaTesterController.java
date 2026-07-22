package com.nodo.retotecnico.controllers;

import com.nodo.retotecnico.models.BetaTester;
import com.nodo.retotecnico.services.BetaTesterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/beta-testers")
public class BetaTesterController {

    private final BetaTesterService betaTesterService;

    public BetaTesterController(BetaTesterService betaTesterService) {
        this.betaTesterService = betaTesterService;
    }

    @GetMapping
    public ResponseEntity<List<BetaTester>> getAllBetaTesters() {
        return ResponseEntity.ok(betaTesterService.getAllBetaTesters());
    }

    @GetMapping("/{email}")
    public ResponseEntity<BetaTester> getBetaTesterByEmail(@PathVariable String email) {
        return betaTesterService.getBetaTesterByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BetaTester> createBetaTester(@RequestBody BetaTester betaTester) {
        BetaTester newBetaTester = betaTesterService.createBetaTester(betaTester);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBetaTester);
    }

    @PutMapping("/{email}")
    public ResponseEntity<BetaTester> updateBetaTester(@PathVariable String email, @RequestBody BetaTester betaTester) {
        try {
            BetaTester updated = betaTesterService.updateBetaTester(email, betaTester);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteBetaTester(@PathVariable String email) {
        try {
            betaTesterService.deleteBetaTester(email);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

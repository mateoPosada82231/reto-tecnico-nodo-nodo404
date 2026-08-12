package com.nodo.retotecnico.controllers;

import com.nodo.retotecnico.dto.BroadcastRequest;
import com.nodo.retotecnico.dto.ExtensionPurchaseStatsDTO;
import com.nodo.retotecnico.dto.PromoteAdminRequest;
import com.nodo.retotecnico.models.Users;
import com.nodo.retotecnico.services.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/users/beta")
    public ResponseEntity<List<Users>> getBetaUsers() {
        return ResponseEntity.ok(adminService.getBetaUsers());
    }

    @GetMapping("/extensions/stats")
    public ResponseEntity<List<ExtensionPurchaseStatsDTO>> getExtensionStats() {
        List<ExtensionPurchaseStatsDTO> stats = adminService.getExtensionPurchaseStats();
        return ResponseEntity.ok(stats);
    }

    @PostMapping("/broadcast")
    public ResponseEntity<String> broadcastEmail(@RequestBody BroadcastRequest request) {
        adminService.broadcastToBetaTesters(request.getSubject(), request.getBody());
        return ResponseEntity.ok("Broadcast enviado a todos los beta testers");
    }

    @PostMapping("/users/promote")
    public ResponseEntity<Users> promoteToAdmin(@RequestBody PromoteAdminRequest request) {
        Users user = adminService.promoteUser(request.getEmail());
        return ResponseEntity.ok(user);
    }
}
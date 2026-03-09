package com.nodo.retotecnico.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nodo.retotecnico.Models.Extensions;
import com.nodo.retotecnico.Services.Extensionsservice;

@RestController
@RequestMapping("/api/extensions")
public class ExtensionsController {

    private final Extensionsservice extensionsService;

    public ExtensionsController(Extensionsservice extensionsService) {
        this.extensionsService = extensionsService;
    }

    @GetMapping
    public ResponseEntity<List<Extensions>> getAllExtensions() {
        List<Extensions> extensions = extensionsService.getAllExtensions();
        return ResponseEntity.ok(extensions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Extensions> getExtensionById(@PathVariable Integer id) {
        return extensionsService.getExtensionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Extensions>> getByCategory(@PathVariable String category) {
        List<Extensions> extensions = extensionsService.getExtensionsByCategory(category);
        return ResponseEntity.ok(extensions);
    }

    @GetMapping("/distributor/{distributor}")
    public ResponseEntity<List<Extensions>> getByDistributor(@PathVariable String distributor) {
        List<Extensions> extensions = extensionsService.getExtensionsByDistributor(distributor);
        return ResponseEntity.ok(extensions);
    }

    @GetMapping("/age/{age}")
    public ResponseEntity<List<Extensions>> getForAge(@PathVariable Integer age) {
        List<Extensions> extensions = extensionsService.getExtensionsForAge(age);
        return ResponseEntity.ok(extensions);
    }

    @PostMapping
    public ResponseEntity<Extensions> createExtension(@RequestBody Extensions extension) {
        Extensions newExtension = extensionsService.createExtension(extension);
        return ResponseEntity.status(HttpStatus.CREATED).body(newExtension);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Extensions> updateExtension(@PathVariable Integer id, @RequestBody Extensions extension) {
        try {
            Extensions updated = extensionsService.updateExtension(id, extension);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExtension(@PathVariable Integer id) {
        try {
            extensionsService.deleteExtension(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
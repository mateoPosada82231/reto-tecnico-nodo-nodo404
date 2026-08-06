package com.nodo.retotecnico.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nodo.retotecnico.dto.ExtensionResponseDTO;
import com.nodo.retotecnico.models.Extensions;
import com.nodo.retotecnico.services.ExtensionsService;

@RestController
@RequestMapping("/api/extensions")
public class ExtensionsController {

    private final ExtensionsService extensionsService;

    public ExtensionsController(ExtensionsService extensionsService) {
        this.extensionsService = extensionsService;
    }

    @GetMapping
    public ResponseEntity<List<ExtensionResponseDTO>> getAllExtensions(
            @RequestParam(defaultValue = "es") String language) {
        return ResponseEntity.ok(extensionsService.getAllExtensions(language));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExtensionResponseDTO> getExtensionById(
            @PathVariable Integer id,
            @RequestParam(defaultValue = "es") String language) {
        return extensionsService.getExtensionById(id, language)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ExtensionResponseDTO>> getByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "es") String language) {
        return ResponseEntity.ok(extensionsService.getExtensionsByCategory(category, language));
    }

    @GetMapping("/distributor/{distributor}")
    public ResponseEntity<List<ExtensionResponseDTO>> getByDistributor(
            @PathVariable String distributor,
            @RequestParam(defaultValue = "es") String language) {
        return ResponseEntity.ok(extensionsService.getExtensionsByDistributor(distributor, language));
    }

    @GetMapping("/age/{age}")
    public ResponseEntity<List<ExtensionResponseDTO>> getForAge(
            @PathVariable Integer age,
            @RequestParam(defaultValue = "es") String language) {
        return ResponseEntity.ok(extensionsService.getExtensionsForAge(age, language));
    }

    @GetMapping("/trending")
    public ResponseEntity<List<ExtensionResponseDTO>> getTrending(
            @RequestParam(defaultValue = "es") String language) {
        return ResponseEntity.ok(extensionsService.getTrendingExtension(language));
    }

    @GetMapping("/random")
    public ResponseEntity<List<ExtensionResponseDTO>> getRandom(
            @RequestParam(defaultValue = "es") String language) {
        return ResponseEntity.ok(extensionsService.getRandomExtension(language));
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
    public ResponseEntity<Map<String, String>> deleteExtension(@PathVariable Integer id) {
        try {
            extensionsService.deleteExtension(id);
            return ResponseEntity.ok(Map.of("message", "Extension eliminada con exito"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Extension no encontrada"));
        }
    }
}

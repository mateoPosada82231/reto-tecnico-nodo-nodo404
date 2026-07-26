package com.nodo.retotecnico.controllers;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.http.CacheControl;
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

import com.nodo.retotecnico.dto.SiteConfigRequest;
import com.nodo.retotecnico.models.SiteConfig;
import com.nodo.retotecnico.services.SiteConfigService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/config")
public class SiteConfigController {

    private final SiteConfigService siteConfigService;

    public SiteConfigController(SiteConfigService siteConfigService) {
        this.siteConfigService = siteConfigService;
    }

    @GetMapping("/{configKey}")
    public ResponseEntity<Map<String, Object>> getByKey(@PathVariable String configKey) {
        return siteConfigService.getByConfigKey(configKey)
                .map(config -> {
                    Map<String, Object> response = Map.of(
                            "key", config.getConfigKey(),
                            "value", config.getConfigValue());
                    return ResponseEntity.ok()
                            .cacheControl(CacheControl.maxAge(5, TimeUnit.MINUTES))
                            .body(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SiteConfig> create(@Valid @RequestBody SiteConfigRequest request) {
        SiteConfig config = new SiteConfig();
        config.setConfigKey(request.getConfigKey());
        config.setConfigValue(request.getConfigValue());

        try {
            SiteConfig created = siteConfigService.create(config);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SiteConfig> update(@PathVariable Long id,
            @Valid @RequestBody SiteConfigRequest request) {
        SiteConfig config = new SiteConfig();
        config.setConfigKey(request.getConfigKey());
        config.setConfigValue(request.getConfigValue());

        try {
            SiteConfig updated = siteConfigService.update(id, config);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        try {
            siteConfigService.delete(id);
            return ResponseEntity.ok(Map.of("message", "Config deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Config not found"));
        }
    }
}

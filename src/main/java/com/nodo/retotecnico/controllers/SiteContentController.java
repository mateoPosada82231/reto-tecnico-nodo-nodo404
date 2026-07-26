package com.nodo.retotecnico.controllers;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nodo.retotecnico.dto.SiteContentRequest;
import com.nodo.retotecnico.models.SiteContent;
import com.nodo.retotecnico.services.SiteContentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/content")
public class SiteContentController {

    private final SiteContentService siteContentService;

    public SiteContentController(SiteContentService siteContentService) {
        this.siteContentService = siteContentService;
    }

    @GetMapping("/{sectionKey}")
    public ResponseEntity<Map<String, Object>> getBySection(
            @PathVariable String sectionKey,
            @RequestParam(defaultValue = "es") String language) {

        List<SiteContent> items = siteContentService.getBySectionKey(sectionKey, language);

        List<Map<String, String>> mappedItems = items.stream()
                .map(item -> Map.of(
                        "key", item.getContentKey(),
                        "value", item.getContentValue(),
                        "type", item.getContentType()))
                .collect(Collectors.toList());

        Map<String, Object> response = Map.of(
                "section", sectionKey,
                "items", mappedItems);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(5, TimeUnit.MINUTES))
                .body(response);
    }

    @GetMapping("/{sectionKey}/{contentKey}")
    public ResponseEntity<Map<String, String>> getBySectionAndKey(
            @PathVariable String sectionKey,
            @PathVariable String contentKey,
            @RequestParam(defaultValue = "es") String language) {

        return siteContentService.getBySectionKeyAndContentKey(sectionKey, contentKey, language)
                .map(item -> {
                    Map<String, String> response = Map.of(
                            "key", item.getContentKey(),
                            "value", item.getContentValue(),
                            "type", item.getContentType());
                    return ResponseEntity.ok()
                            .cacheControl(CacheControl.maxAge(5, TimeUnit.MINUTES))
                            .body(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SiteContent> create(@Valid @RequestBody SiteContentRequest request) {
        SiteContent content = new SiteContent();
        content.setSectionKey(request.getSectionKey());
        content.setContentKey(request.getContentKey());
        content.setContentValue(request.getContentValue());
        content.setContentType(request.getContentType());
        content.setLanguage(request.getLanguage());

        try {
            SiteContent created = siteContentService.create(content);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SiteContent> update(@PathVariable Long id,
            @Valid @RequestBody SiteContentRequest request) {
        SiteContent content = new SiteContent();
        content.setSectionKey(request.getSectionKey());
        content.setContentKey(request.getContentKey());
        content.setContentValue(request.getContentValue());
        content.setContentType(request.getContentType());
        content.setLanguage(request.getLanguage());

        try {
            SiteContent updated = siteContentService.update(id, content);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        try {
            siteContentService.delete(id);
            return ResponseEntity.ok(Map.of("message", "Content deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Content not found"));
        }
    }
}

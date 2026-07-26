package com.nodo.retotecnico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SiteContentRequest {

    @NotBlank(message = "sectionKey is required")
    @Pattern(regexp = "^[a-z0-9_.]+$", message = "sectionKey must be lowercase alphanumeric with dots and underscores")
    private String sectionKey;

    @NotBlank(message = "contentKey is required")
    @Pattern(regexp = "^[a-z0-9_.]+$", message = "contentKey must be lowercase alphanumeric with dots and underscores")
    private String contentKey;

    @NotBlank(message = "contentValue is required")
    private String contentValue;

    private String contentType = "text";

    private String language = "es";
}

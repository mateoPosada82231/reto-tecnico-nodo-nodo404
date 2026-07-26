package com.nodo.retotecnico.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SiteConfigRequest {

    @NotBlank(message = "configKey is required")
    private String configKey;

    @NotBlank(message = "configValue is required")
    private String configValue;
}

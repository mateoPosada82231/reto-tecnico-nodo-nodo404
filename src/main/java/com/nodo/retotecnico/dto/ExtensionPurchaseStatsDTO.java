package com.nodo.retotecnico.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExtensionPurchaseStatsDTO {
    private Integer extensionId;
    private String name;
    private String image;
    @JsonProperty("isPublic")
    private boolean isPublic;
    private long purchaseCount;
}
package com.nodo.retotecnico.dto;

import lombok.Data;

@Data
public class CartRequest {
    private String email;
    private Integer extensionId;
    private String language;
    private String platform;
}
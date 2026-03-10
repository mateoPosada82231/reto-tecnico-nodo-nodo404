package com.nodo.retotecnico.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyRequest {

    private String userEmail;
    private Integer extensionId;
    private String paymentMethod;
}


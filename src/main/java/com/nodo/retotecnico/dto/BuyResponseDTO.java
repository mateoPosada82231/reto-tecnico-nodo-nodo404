package com.nodo.retotecnico.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuyResponseDTO {
    private Integer id;
    private LocalDate date;
    private String paymentMethod;
    private String language;
    private String platform;
    private String userEmail;
    private ExtensionResponseDTO extension;
    private BigDecimal extensionPrice;
}

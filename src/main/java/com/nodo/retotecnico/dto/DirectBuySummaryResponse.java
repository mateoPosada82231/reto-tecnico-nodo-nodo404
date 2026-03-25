package com.nodo.retotecnico.dto;

import com.nodo.retotecnico.models.Buys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectBuySummaryResponse {

    private Buys buy;
    private BigDecimal totalPrice;
    private String message;
}


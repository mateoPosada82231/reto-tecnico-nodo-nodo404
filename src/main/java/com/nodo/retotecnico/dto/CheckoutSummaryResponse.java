package com.nodo.retotecnico.dto;

import com.nodo.retotecnico.models.Buys;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutSummaryResponse {

    private List<Buys> buys;
    private Integer itemsCount;
    private BigDecimal totalPrice;
    private String message;
}


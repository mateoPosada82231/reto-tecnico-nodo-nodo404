package com.nodo.retotecnico.dto;

import com.nodo.retotecnico.models.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartSummaryResponse {

    private List<CartItem> items;
    private Integer itemsCount;
    private BigDecimal totalPrice;
}


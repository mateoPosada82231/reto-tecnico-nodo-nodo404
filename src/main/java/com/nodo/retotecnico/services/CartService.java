package com.nodo.retotecnico.services;

import com.nodo.retotecnico.models.CartItem;
import com.nodo.retotecnico.dto.CartRequest;
import com.nodo.retotecnico.dto.CartSummaryResponse;

public interface CartService {

    CartSummaryResponse getCartByEmail(String email);

    CartItem addToCart(CartRequest request);

    void deleteItem(Integer cartItemId, String email);

    void clearCart(String email);
}

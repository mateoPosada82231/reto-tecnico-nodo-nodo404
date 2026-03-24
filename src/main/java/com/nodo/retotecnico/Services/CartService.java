package com.nodo.retotecnico.Services;

import com.nodo.retotecnico.Models.Buys;
import com.nodo.retotecnico.dto.CartRequest;
import java.util.List;

public interface CartService {

    List<Buys> getCartByEmail(String email);

    Buys addToCart(CartRequest request);

    // NUEVOS MÉTODOS
    void deleteItem(Integer cartItemId, String email);

    void clearCart(String email);
}

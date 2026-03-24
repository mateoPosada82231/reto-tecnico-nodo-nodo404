package com.nodo.retotecnico.serviceImpl;

import com.nodo.retotecnico.models.*;
import com.nodo.retotecnico.repositories.*;
import com.nodo.retotecnico.services.CartService;
import com.nodo.retotecnico.dto.CartRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final UsersRepository usersRepository;
    private final ExtensionsRepository extensionsRepository;

    @Override
    public List<CartItem> getCartByEmail(String email) {
        return cartItemRepository.findByUserEmail(email);
    }

    @Override
    public CartItem addToCart(CartRequest request) {

        Users user = usersRepository.findById(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Extensions extension = extensionsRepository.findById(request.getExtensionId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (cartItemRepository.existsByUserAndExtension(user, extension)) {
            throw new RuntimeException("El producto ya está en el carrito");
        }

        CartItem newCartItem = new CartItem();
        newCartItem.setUser(user);
        newCartItem.setExtension(extension);
        newCartItem.setAddedDate(LocalDate.now());

        return cartItemRepository.save(newCartItem);
    }

    @Override
    public void deleteItem(Integer cartItemId, String email) {
        cartItemRepository.deleteByIdAndUserEmail(cartItemId, email);
    }

    @Override
    public void clearCart(String email) {
        cartItemRepository.deleteByUserEmail(email);
    }
}

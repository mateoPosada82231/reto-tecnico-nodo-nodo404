package com.nodo.retotecnico.serviceImpl;

import com.nodo.retotecnico.dto.CartItemResponseDTO;
import com.nodo.retotecnico.dto.CartRequest;
import com.nodo.retotecnico.dto.CartSummaryResponse;
import com.nodo.retotecnico.models.CartItem;
import com.nodo.retotecnico.models.Extensions;
import com.nodo.retotecnico.models.Users;
import com.nodo.retotecnico.repositories.CartItemRepository;
import com.nodo.retotecnico.repositories.ExtensionsRepository;
import com.nodo.retotecnico.repositories.UsersRepository;
import com.nodo.retotecnico.services.CartService;
import com.nodo.retotecnico.services.ExtensionsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final UsersRepository usersRepository;
    private final ExtensionsRepository extensionsRepository;
    private final ExtensionsService extensionsService;

    @Override
    public CartSummaryResponse getCartByEmail(String email, String language) {
        List<CartItem> items = cartItemRepository.findByUserEmail(email);
        BigDecimal totalPrice = items.stream()
                .map(item -> item.getExtension() != null ? item.getExtension().getPrice() : null)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<CartItemResponseDTO> responseItems = items.stream()
                .map(item -> new CartItemResponseDTO(
                        item.getId(),
                        item.getAddedDate(),
                        item.getLanguage(),
                        item.getPlatform(),
                        item.getUser() != null ? item.getUser().getEmail() : null,
                        item.getExtension() != null ? extensionsService.toDto(item.getExtension(), language) : null))
                .toList();

        return new CartSummaryResponse(responseItems, items.size(), totalPrice);
    }

    @Override
    public CartItem addToCart(CartRequest request) {

        if (isBlank(request.getLanguage()) || isBlank(request.getPlatform())) {
            throw new RuntimeException("Los campos language y platform son obligatorios");
        }

        Users user = usersRepository.findById(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Extensions extension = extensionsRepository.findById(request.getExtensionId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        if (cartItemRepository.existsByUserAndExtensionAndLanguageAndPlatform(
                user,
                extension,
                request.getLanguage(),
                request.getPlatform())) {
            throw new RuntimeException("El producto ya está en el carrito");
        }

        CartItem newCartItem = new CartItem();
        newCartItem.setUser(user);
        newCartItem.setExtension(extension);
        newCartItem.setLanguage(request.getLanguage());
        newCartItem.setPlatform(request.getPlatform());
        newCartItem.setAddedDate(LocalDate.now());

        return cartItemRepository.save(newCartItem);
    }

    @Override
    @Transactional
    public void deleteItem(Integer cartItemId, String email) {
        cartItemRepository.deleteByIdAndUserEmail(cartItemId, email);
    }

    @Override
    @Transactional
    public void clearCart(String email) {
        cartItemRepository.deleteByUserEmail(email);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}

package com.nodo.retotecnico.ServiceImpl;

import com.nodo.retotecnico.Models.*;
import com.nodo.retotecnico.Repositories.*;
import com.nodo.retotecnico.Services.CartService;
import com.nodo.retotecnico.dto.CartRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final Buysrepository buysRepository;
    private final UsersRepository usersRepository;
    private final Extensionsrepository extensionsRepository;

    @Override
    public List<Buys> getCartByEmail(String email) {

        return buysRepository.findByUserEmail(email);
    }

    @Override
    public Buys addToCart(CartRequest request) {
    
        Users user = usersRepository.findById(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

       
        Extensions extension = extensionsRepository.findById(request.getExtensionId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        
        if (buysRepository.existsByUserAndExtension(user, extension)) {
            throw new RuntimeException("El producto ya está en el carrito");
        }


        Buys newCartItem = new Buys();
        newCartItem.setUser(user);
        newCartItem.setExtension(extension);
        newCartItem.setDate(LocalDate.now());
        newCartItem.setPaymentMethod("PENDIENTE"); 

        return buysRepository.save(newCartItem);
    }
}
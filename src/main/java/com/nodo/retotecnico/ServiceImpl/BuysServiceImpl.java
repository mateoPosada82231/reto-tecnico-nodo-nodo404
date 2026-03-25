package com.nodo.retotecnico.serviceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nodo.retotecnico.models.Buys;
import com.nodo.retotecnico.models.Extensions;
import com.nodo.retotecnico.models.Users;
import com.nodo.retotecnico.repositories.BuysRepository;
import com.nodo.retotecnico.repositories.ExtensionsRepository;
import com.nodo.retotecnico.repositories.UsersRepository;
import com.nodo.retotecnico.services.BuysService;

@Service
public class BuysServiceImpl implements BuysService {

    @Autowired
    private BuysRepository buysRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ExtensionsRepository extensionsRepository;

    @Autowired
    private com.nodo.retotecnico.repositories.CartItemRepository cartItemRepository;

    @Override
    public List<Buys> getAllBuys() {
        return buysRepository.findAll();
    }

    @Override
    public Optional<Buys> getBuyById(Integer id) {
        return buysRepository.findById(id);
    }

    @Override
    public List<Buys> getBuysByUserEmail(String email) {
        return buysRepository.findByUserEmail(email);
    }

    @Override
    public List<Buys> getBuysByExtensionId(Integer extensionId) {
        return buysRepository.findByExtension(null);
    }

    @Override
    public List<Buys> getBuysByDateRange(LocalDate startDate, LocalDate endDate) {
        return buysRepository.findByDateBetween(startDate, endDate);
    }

    @Override
    public Buys createBuy(String userEmail, Integer extensionId, String paymentMethod, String language, String platform) {
        Users user = usersRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found: " + userEmail));

        Extensions extension = extensionsRepository.findById(extensionId)
                .orElseThrow(() -> new RuntimeException("Extension not found: " + extensionId));

        Buys newBuy = new Buys();
        newBuy.setDate(LocalDate.now());
        newBuy.setPaymentMethod(paymentMethod);
        newBuy.setLanguage(language);
        newBuy.setPlatform(platform);
        newBuy.setUser(user);
        newBuy.setExtension(extension);
        return buysRepository.save(newBuy);
    }

    @Override
    public void deleteBuy(Integer id) {
        buysRepository.deleteById(id);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public void checkout(com.nodo.retotecnico.dto.BuyRequest request) {

        Users user = usersRepository.findByEmail(request.getUserEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + request.getUserEmail()));


        List<com.nodo.retotecnico.models.CartItem> items = cartItemRepository.findByUserEmail(request.getUserEmail());

        if (items.isEmpty()) {
            throw new RuntimeException("El carrito está vacío, no hay nada que comprar.");
        }


        for (com.nodo.retotecnico.models.CartItem item : items) {
            Buys buy = new Buys();
            buy.setDate(LocalDate.now()); //
            buy.setPaymentMethod(request.getPaymentMethod());
            buy.setLanguage(item.getLanguage());
            buy.setPlatform(item.getPlatform());
            buy.setUser(user);
            buy.setExtension(item.getExtension());

            buysRepository.save(buy);
        }


        cartItemRepository.deleteByUserEmail(request.getUserEmail());
    }
}
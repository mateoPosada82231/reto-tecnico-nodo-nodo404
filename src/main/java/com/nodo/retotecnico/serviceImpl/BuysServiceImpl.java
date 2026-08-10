package com.nodo.retotecnico.serviceImpl;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.nodo.retotecnico.models.Buys;
import com.nodo.retotecnico.models.Extensions;
import com.nodo.retotecnico.models.Users;
import com.nodo.retotecnico.dto.BuyResponseDTO;
import com.nodo.retotecnico.dto.CheckoutSummaryResponse;
import com.nodo.retotecnico.repositories.BuysRepository;
import com.nodo.retotecnico.repositories.ExtensionsRepository;
import com.nodo.retotecnico.repositories.UsersRepository;
import com.nodo.retotecnico.services.BuysService;
import com.nodo.retotecnico.services.EmailService;
import com.nodo.retotecnico.services.ExtensionsService;

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

    @Autowired
    private ExtensionsService extensionsService;

    @Autowired
    private EmailService emailService;

    @Override
    public List<Buys> getAllBuys() {
        return buysRepository.findAll();
    }

    @Override
    public Optional<Buys> getBuyById(Integer id) {
        return buysRepository.findById(id);
    }

    @Override
    public List<BuyResponseDTO> getBuysByUserEmail(String email, String language) {
        return buysRepository.findByUserEmail(email).stream()
                .map(buy -> toDto(buy, language))
                .collect(java.util.stream.Collectors.toList());
    }

    private BuyResponseDTO toDto(Buys buy, String language) {
        Extensions ext = buy.getExtension();
        return new BuyResponseDTO(
                buy.getId(),
                buy.getDate(),
                buy.getPaymentMethod(),
                buy.getLanguage(),
                buy.getPlatform(),
                buy.getUser() != null ? buy.getUser().getEmail() : null,
                ext != null ? extensionsService.toDto(ext, language) : null,
                ext != null ? ext.getPrice() : null);
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

        if (buysRepository.existsByUserAndExtension(user, extension)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya has comprado esta extensión");
        }

        Buys newBuy = new Buys();
        newBuy.setDate(LocalDate.now());
        newBuy.setPaymentMethod(paymentMethod);
        newBuy.setLanguage(language);
        newBuy.setPlatform(platform);
        newBuy.setUser(user);
        newBuy.setExtension(extension);
        Buys saved = buysRepository.save(newBuy);

        BigDecimal price = extension.getPrice() != null ? extension.getPrice() : BigDecimal.ZERO;
        emailService.sendPurchaseEmail(user.getEmail(), user.getFullName(), List.of(extension.getName()), price);

        return saved;
    }

    @Override
    public void deleteBuy(Integer id) {
        buysRepository.deleteById(id);
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public CheckoutSummaryResponse checkout(com.nodo.retotecnico.dto.BuyRequest request) {

        Users user = usersRepository.findByEmail(request.getUserEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado: " + request.getUserEmail()));


        List<com.nodo.retotecnico.models.CartItem> items = cartItemRepository.findByUserEmail(request.getUserEmail());

        if (items.isEmpty()) {
            throw new RuntimeException("El carrito está vacío, no hay nada que comprar.");
        }

        List<Buys> createdBuys = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (com.nodo.retotecnico.models.CartItem item : items) {
            if (item.getExtension() != null && buysRepository.existsByUserAndExtension(user, item.getExtension())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya has comprado la extensión: " + item.getExtension().getName());
            }

            Buys buy = new Buys();
            buy.setDate(LocalDate.now());
            buy.setPaymentMethod(request.getPaymentMethod());
            buy.setLanguage(item.getLanguage());
            buy.setPlatform(item.getPlatform());
            buy.setUser(user);
            buy.setExtension(item.getExtension());

            createdBuys.add(buysRepository.save(buy));
            if (item.getExtension() != null && item.getExtension().getPrice() != null) {
                totalPrice = totalPrice.add(item.getExtension().getPrice());
            }
        }


        cartItemRepository.deleteByUserEmail(request.getUserEmail());

        List<String> extensionNames = createdBuys.stream()
                .map(Buys::getExtension)
                .filter(java.util.Objects::nonNull)
                .map(Extensions::getName)
                .collect(java.util.stream.Collectors.toList());

        emailService.sendPurchaseEmail(user.getEmail(), user.getFullName(), extensionNames, totalPrice);

        return new CheckoutSummaryResponse(
                createdBuys,
                createdBuys.size(),
                totalPrice,
                "Compra realizada con exito y carrito vaciado."
        );
    }
}
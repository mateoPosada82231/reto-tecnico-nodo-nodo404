package com.nodo.retotecnico.Services;

import com.nodo.retotecnico.Models.Buys;
import com.nodo.retotecnico.Models.Extensions;
import com.nodo.retotecnico.Models.Users;
import com.nodo.retotecnico.Repositories.BuysRepository;
import com.nodo.retotecnico.Repositories.ExtensionsRepository;
import com.nodo.retotecnico.Repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class BuysService {

    private final BuysRepository buysRepository;
    private final UsersRepository usersRepository;
    private final ExtensionsRepository extensionsRepository;

    public List<Buys> getAllBuys() {
        return buysRepository.findAll();
    }

    public Optional<Buys> getBuyById(Integer id) {
        return buysRepository.findById(id);
    }

    public List<Buys> getBuysByUserEmail(String email) {
        return buysRepository.findByUserEmail(email);
    }

    public List<Buys> getBuysByExtensionId(Integer extensionId) {
        Extensions extension = extensionsRepository.findById(extensionId)
                .orElseThrow(() -> new RuntimeException("Extension no encontrada con id: " + extensionId));
        return buysRepository.findByExtension(extension);
    }

    public List<Buys> getBuysByDateRange(LocalDate startDate, LocalDate endDate) {
        return buysRepository.findByDateBetween(startDate, endDate);
    }

    public Buys createBuy(String userEmail, Integer extensionId, String paymentMethod) {
        Users user = usersRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + userEmail));

        Extensions extension = extensionsRepository.findById(extensionId)
                .orElseThrow(() -> new RuntimeException("Extension no encontrada con id: " + extensionId));

        if (buysRepository.existsByUserAndExtension(user, extension)) {
            throw new RuntimeException("El usuario ya compró esta extensión.");
        }

        Buys buy = new Buys();
        buy.setUser(user);
        buy.setExtension(extension);
        buy.setPaymentMethod(paymentMethod);
        buy.setDate(LocalDate.now());

        return buysRepository.save(buy);
    }

    public void deleteBuy(Integer id) {
        if (!buysRepository.existsById(id)) {
            throw new RuntimeException("Compra no encontrada con id: " + id);
        }
        buysRepository.deleteById(id);
    }
}
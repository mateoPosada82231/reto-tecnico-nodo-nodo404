package com.nodo.retotecnico.ServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nodo.retotecnico.Models.Buys;
import com.nodo.retotecnico.Models.Extensions;
import com.nodo.retotecnico.Models.Users;
import com.nodo.retotecnico.Repositories.Buysrepository;
import com.nodo.retotecnico.Repositories.Extensionsrepository;
import com.nodo.retotecnico.Repositories.UsersRepository;
import com.nodo.retotecnico.Services.Buysservice;

@Service
public class BuysServiceImpl implements Buysservice {

    @Autowired
    private Buysrepository buysRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private Extensionsrepository extensionsRepository;

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
    public Buys createBuy(String userEmail, Integer extensionId, String paymentMethod) {
        Users user = usersRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found: " + userEmail));

        Extensions extension = extensionsRepository.findById(extensionId)
                .orElseThrow(() -> new RuntimeException("Extension not found: " + extensionId));

        Buys newBuy = new Buys();
        newBuy.setDate(LocalDate.now());
        newBuy.setPaymentMethod(paymentMethod);
        newBuy.setUser(user);
        newBuy.setExtension(extension);
        return buysRepository.save(newBuy);
    }

    @Override
    public void deleteBuy(Integer id) {
        buysRepository.deleteById(id);
    }
}
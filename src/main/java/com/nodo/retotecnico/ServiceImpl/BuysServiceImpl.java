package com.nodo.retotecnico.ServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nodo.retotecnico.Models.Buys;
import com.nodo.retotecnico.Repositories.BuysRepository;
import com.nodo.retotecnico.Services.BuysService;

@Service
public class BuysServiceImpl implements BuysService {

    @Autowired
    private BuysRepository buysRepository;

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
        Buys newBuy = new Buys();
        return buysRepository.save(newBuy);
    }

    @Override
    public void deleteBuy(Integer id) {
        buysRepository.deleteById(id);
    }
}
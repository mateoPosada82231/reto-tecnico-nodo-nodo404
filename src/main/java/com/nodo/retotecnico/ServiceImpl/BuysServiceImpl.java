package com.nodo.retotecnico.ServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nodo.retotecnico.Models.Buys;
import com.nodo.retotecnico.Repositories.Buysrepository;
import com.nodo.retotecnico.Services.Buysservice;

@Service
public class BuysServiceImpl implements Buysservice {

    @Autowired
    private Buysrepository buysrepository;

    @Override
    public List<Buys> getAllBuys() {
        return buysrepository.findAll();
    }

    @Override
    public Optional<Buys> getBuyById(Integer id) {
        return buysrepository.findById(id);
    }

    @Override
    public List<Buys> getBuysByUserEmail(String email) {
        return buysrepository.findByUserEmail(email);
    }

    @Override
    public List<Buys> getBuysByExtensionId(Integer extensionId) {
 
        return buysrepository.findByExtension(null);
    }

    @Override
    public List<Buys> getBuysByDateRange(LocalDate startDate, LocalDate endDate) {
        return buysrepository.findByDateBetween(startDate, endDate);
    }

    @Override
    public Buys createBuy(String userEmail, Integer extensionId, String paymentMethod) {
        Buys newBuy = new Buys();
        return buysrepository.save(newBuy);
    }

    @Override
    public void deleteBuy(Integer id) {
        buysrepository.deleteById(id);
    }
}
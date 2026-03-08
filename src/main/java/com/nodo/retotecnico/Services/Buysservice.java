package com.nodo.retotecnico.Services;

import com.nodo.retotecnico.Models.Buys;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

interface BuysService {

    List<Buys> getAllBuys();

    Optional<Buys> getBuyById(Integer id);

    List<Buys> getBuysByUserEmail(String email);

    List<Buys> getBuysByExtensionId(Integer extensionId);

    List<Buys> getBuysByDateRange(LocalDate startDate, LocalDate endDate);

    Buys createBuy(String userEmail, Integer extensionId, String paymentMethod);

    void deleteBuy(Integer id);
}
package com.nodo.retotecnico.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import com.nodo.retotecnico.models.Buys;
import com.nodo.retotecnico.dto.BuyRequest;

public interface BuysService {

    List<Buys> getAllBuys();

    Optional<Buys> getBuyById(Integer id);

    List<Buys> getBuysByUserEmail(String email);

    List<Buys> getBuysByExtensionId(Integer extensionId);

    List<Buys> getBuysByDateRange(LocalDate startDate, LocalDate endDate);

    Buys createBuy(String userEmail, Integer extensionId, String paymentMethod);

    void deleteBuy(Integer id);
    void checkout(BuyRequest request);
}

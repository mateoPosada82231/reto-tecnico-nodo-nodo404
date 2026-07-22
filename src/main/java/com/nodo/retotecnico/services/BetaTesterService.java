package com.nodo.retotecnico.services;

import com.nodo.retotecnico.models.BetaTester;

import java.util.List;
import java.util.Optional;

public interface BetaTesterService {

    List<BetaTester> getAllBetaTesters();

    Optional<BetaTester> getBetaTesterByEmail(String email);

    BetaTester createBetaTester(BetaTester betaTester);

    BetaTester updateBetaTester(String email, BetaTester updatedBetaTester);

    void deleteBetaTester(String email);

    boolean verifyPassword(String rawPassword, String email);
}

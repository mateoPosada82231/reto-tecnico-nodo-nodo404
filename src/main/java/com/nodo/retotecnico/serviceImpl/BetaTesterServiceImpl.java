package com.nodo.retotecnico.serviceImpl;

import com.nodo.retotecnico.models.BetaTester;
import com.nodo.retotecnico.repositories.BetaTesterRepository;
import com.nodo.retotecnico.services.BetaTesterService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BetaTesterServiceImpl implements BetaTesterService {

    private final BetaTesterRepository betaTesterRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public BetaTesterServiceImpl(BetaTesterRepository betaTesterRepository, BCryptPasswordEncoder passwordEncoder) {
        this.betaTesterRepository = betaTesterRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BetaTester> getAllBetaTesters() {
        return betaTesterRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BetaTester> getBetaTesterByEmail(String email) {
        return betaTesterRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public BetaTester createBetaTester(BetaTester betaTester) {
        betaTester.setPassword(passwordEncoder.encode(betaTester.getPassword()));
        betaTester.setDateOfAdmission(LocalDate.now());
        return betaTesterRepository.save(betaTester);
    }

    @Override
    @Transactional
    public BetaTester updateBetaTester(String email, BetaTester updatedBetaTester) {
        BetaTester existing = betaTesterRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("BetaTester not found: " + email));
        existing.setCountry(updatedBetaTester.getCountry());
        existing.setDateOfBirth(updatedBetaTester.getDateOfBirth());
        existing.setIdentification(updatedBetaTester.getIdentification());
        existing.setFullName(updatedBetaTester.getFullName());
        existing.setMobileNumber(updatedBetaTester.getMobileNumber());
        existing.setDateOfAdmission(updatedBetaTester.getDateOfAdmission());
        existing.setPassword(passwordEncoder.encode(updatedBetaTester.getPassword()));
        return betaTesterRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteBetaTester(String email) {
        if (!betaTesterRepository.existsByEmail(email)) {
            throw new RuntimeException("BetaTester not found: " + email);
        }
        betaTesterRepository.deleteById(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean verifyPassword(String rawPassword, String email) {
        BetaTester betaTester = betaTesterRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("BetaTester not found: " + email));
        return passwordEncoder.matches(rawPassword, betaTester.getPassword());
    }
}

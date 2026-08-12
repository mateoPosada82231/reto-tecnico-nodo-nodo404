package com.nodo.retotecnico.serviceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nodo.retotecnico.models.Users;
import com.nodo.retotecnico.repositories.UsersRepository;
import com.nodo.retotecnico.services.EmailService;
import com.nodo.retotecnico.services.UsersService;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public UsersServiceImpl(UsersRepository usersRepository, BCryptPasswordEncoder passwordEncoder, EmailService emailService) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Users> getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public Users createUser(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setDateOfAdmission(LocalDate.now());
        return usersRepository.save(user);
    }

    @Override
    @Transactional
    public Users updateUser(String email, Users updatedUser) {
        Users existing = usersRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));
        if (updatedUser.getCountry() != null) {
            existing.setCountry(updatedUser.getCountry());
        }
        if (updatedUser.getDateOfBirth() != null) {
            existing.setDateOfBirth(updatedUser.getDateOfBirth());
        }
        if (updatedUser.getIdentification() != null) {
            existing.setIdentification(updatedUser.getIdentification());
        }
        if (updatedUser.getFullName() != null) {
            existing.setFullName(updatedUser.getFullName());
        }
        if (updatedUser.getMobileNumber() != null) {
            existing.setMobileNumber(updatedUser.getMobileNumber());
        }
        if (updatedUser.isBetaTester() && !existing.isBetaTester()) {
            existing.setBetaTester(true);
        }
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        return usersRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteUser(String email) {
        if (!usersRepository.existsByEmail(email)) {
            throw new RuntimeException("User not found: " + email);
        }
        usersRepository.deleteById(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean verifyPassword(String rawPassword, String email) {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    @Override
    @Transactional
    public void changePassword(String email, String currentPassword, String newPassword) {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        usersRepository.save(user);

        emailService.sendPasswordChangedEmail(user.getEmail(), user.getFullName());
    }

    @Override
    @Transactional
    public void resetPassword(String email, String newPassword) {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        usersRepository.save(user);

        emailService.sendPasswordChangedEmail(user.getEmail(), user.getFullName());
    }
}
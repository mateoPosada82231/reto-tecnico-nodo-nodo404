package com.nodo.retotecnico.ServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nodo.retotecnico.Models.Users;
import com.nodo.retotecnico.Repositories.UsersRepository;
import com.nodo.retotecnico.Services.UsersService;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsersServiceImpl(UsersRepository usersRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
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
        existing.setCountry(updatedUser.getCountry());
        existing.setDateOfBirth(updatedUser.getDateOfBirth());
        existing.setIdentification(updatedUser.getIdentification());
        existing.setFullName(updatedUser.getFullName());
        existing.setMobileNumber(updatedUser.getMobileNumber());
        existing.setDateOfAdmission(updatedUser.getDateOfAdmission());
        existing.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
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
}
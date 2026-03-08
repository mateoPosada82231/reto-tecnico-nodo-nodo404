package com.nodo.retotecnico.Services;

import com.nodo.retotecnico.Models.Users;
import com.nodo.retotecnico.Repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;

    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    public Optional<Users> getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public Users createUser(Users user) {
        if (usersRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Ya existe un usuario con el email: " + user.getEmail());
        }
        return usersRepository.save(user);
    }

    public Users updateUser(String email, Users updatedUser) {
        Users existing = usersRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));

        existing.setCountry(updatedUser.getCountry());
        existing.setDateOfBirth(updatedUser.getDateOfBirth());
        existing.setIdentification(updatedUser.getIdentification());
        existing.setFullName(updatedUser.getFullName());
        existing.setMobileNumber(updatedUser.getMobileNumber());
        existing.setPassword(updatedUser.getPassword());

        return usersRepository.save(existing);
    }

    public void deleteUser(String email) {
        if (!usersRepository.existsByEmail(email)) {
            throw new RuntimeException("Usuario no encontrado con email: " + email);
        }
        usersRepository.deleteById(email);
    }
}
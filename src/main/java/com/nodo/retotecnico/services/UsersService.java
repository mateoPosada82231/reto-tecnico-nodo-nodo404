package com.nodo.retotecnico.services;

import java.util.List;
import java.util.Optional;

import com.nodo.retotecnico.models.Users;

public interface UsersService {

    List<Users> getAllUsers();

    Optional<Users> getUserByEmail(String email);

    Users createUser(Users user);

    Users updateUser(String email, Users updatedUser);

    void deleteUser(String email);

    boolean verifyPassword(String rawPassword, String email);
}
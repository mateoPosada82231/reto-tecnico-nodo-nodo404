package com.nodo.retotecnico.Services;

import java.util.List;
import java.util.Optional;

import com.nodo.retotecnico.Models.Users;

public interface UsersService {

    List<Users> getAllUsers();

    Optional<Users> getUserByEmail(String email);

    Users createUser(Users user);

    Users updateUser(String email, Users updatedUser);

    void deleteUser(String email);

    boolean verifyPassword(String rawPassword, String email);
}
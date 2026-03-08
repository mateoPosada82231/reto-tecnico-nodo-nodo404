package com.nodo.retotecnico.Services;

import com.nodo.retotecnico.Models.Users;

import java.util.List;
import java.util.Optional;

public interface UsersService {

    List<Users> getAllUsers();

    Optional<Users> getUserByEmail(String email);

    Users createUser(Users user);

    Users updateUser(String email, Users updatedUser);

    void deleteUser(String email);
}
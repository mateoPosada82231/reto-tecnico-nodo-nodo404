package com.nodo.retotecnico.ServiceImpl;

import java.util.List;
import java.util.Optional;

import com.nodo.retotecnico.Models.Users;

public interface UsersServiceImpl {
    Optional<Users> findByEmail(String email);
    boolean existsByEmail(String email);
    List<Users> findAll();
    Users save(Users user);
}
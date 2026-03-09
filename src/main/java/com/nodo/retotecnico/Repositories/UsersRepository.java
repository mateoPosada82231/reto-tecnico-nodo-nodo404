package com.nodo.retotecnico.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nodo.retotecnico.Models.Users;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {

    Optional<Users> findByEmail(String email);

    boolean existsByEmail(String email);
}
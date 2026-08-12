package com.nodo.retotecnico.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nodo.retotecnico.models.Users;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {

    Optional<Users> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT u.email FROM Users u")
    List<String> findAllEmails();

    List<Users> findByBetaTesterTrue();
}
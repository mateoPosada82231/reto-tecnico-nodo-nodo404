package com.nodo.retotecnico.Repositories;

import com.nodo.retotecnico.Models.Buys;
import com.nodo.retotecnico.Models.Extensions;
import com.nodo.retotecnico.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BuysRepository extends JpaRepository<Buys, Integer> {

    List<Buys> findByUser(Users user);

    List<Buys> findByExtension(Extensions extension);

    List<Buys> findByUserEmail(String userEmail);

    List<Buys> findByDateBetween(LocalDate startDate, LocalDate endDate);

    boolean existsByUserAndExtension(Users user, Extensions extension);
}
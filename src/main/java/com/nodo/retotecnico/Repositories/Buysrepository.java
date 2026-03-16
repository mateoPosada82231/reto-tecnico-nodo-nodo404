package com.nodo.retotecnico.Repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nodo.retotecnico.Models.Buys;
import com.nodo.retotecnico.Models.Extensions;
import com.nodo.retotecnico.Models.Users;

@Repository
public interface Buysrepository extends JpaRepository<Buys, Integer> {

    List<Buys> findByUser(Users user);

    List<Buys> findByExtension(Extensions extension);

    List<Buys> findByUserEmail(String userEmail);

    List<Buys> findByDateBetween(LocalDate startDate, LocalDate endDate);

    boolean existsByUserAndExtension(Users user, Extensions extension);
}

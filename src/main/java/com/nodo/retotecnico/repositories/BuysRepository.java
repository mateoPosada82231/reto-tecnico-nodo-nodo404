package com.nodo.retotecnico.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nodo.retotecnico.models.Buys;
import com.nodo.retotecnico.models.Extensions;
import com.nodo.retotecnico.models.Users;

@Repository
public interface BuysRepository extends JpaRepository<Buys, Integer> {

    List<Buys> findByUser(Users user);

    List<Buys> findByExtension(Extensions extension);

    List<Buys> findByUserEmail(String userEmail);

    List<Buys> findByDateBetween(LocalDate startDate, LocalDate endDate);

    boolean existsByUserAndExtension(Users user, Extensions extension);

    void deleteByUserEmail(String userEmail);

    void deleteByIdAndUserEmail(Integer id, String userEmail);

}

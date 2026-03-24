package com.nodo.retotecnico.repositories;

import com.nodo.retotecnico.models.CartItem;
import com.nodo.retotecnico.models.Extensions;
import com.nodo.retotecnico.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    List<CartItem> findByUserEmail(String email);

    boolean existsByUserAndExtension(Users user, Extensions extension);

    void deleteByUserEmail(String email);

    void deleteByIdAndUserEmail(Integer id, String email);
}
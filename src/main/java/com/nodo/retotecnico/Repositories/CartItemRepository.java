package com.nodo.retotecnico.Repositories;

import com.nodo.retotecnico.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    List<CartItem> findByUserEmail(String email);

    void deleteByUserEmail(String email);

    void deleteByIdAndUserEmail(Integer id, String email);
}
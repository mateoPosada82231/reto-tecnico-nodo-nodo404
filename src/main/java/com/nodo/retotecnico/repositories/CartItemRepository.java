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

    boolean existsByUserAndExtensionAndLanguageAndPlatform(Users user, Extensions extension, String language, String platform);

    void deleteByUserEmail(String email);

    void deleteByIdAndUserEmail(Integer id, String email);
}
package com.nodo.retotecnico.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nodo.retotecnico.models.Extensions;

@Repository
public interface ExtensionsRepository extends JpaRepository<Extensions, Integer> {

    List<Extensions> findByCategory(String category);

    List<Extensions> findByDistributor(String distributor);

    List<Extensions> findByRequiredAgeLessThanEqual(Integer age);
}
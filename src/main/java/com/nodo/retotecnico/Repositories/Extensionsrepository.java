package com.nodo.retotecnico.Repositories;

import com.nodo.retotecnico.Models.Extensions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtensionsRepository extends JpaRepository<Extensions, Integer> {

    List<Extensions> findByCategory(String category);

    List<Extensions> findByDistributor(String distributor);

    List<Extensions> findByRequiredAgeLessThanEqual(Integer age);
}
package com.nodo.retotecnico.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nodo.retotecnico.models.Extensions;

@Repository
public interface ExtensionsRepository extends JpaRepository<Extensions, Integer> {

    List<Extensions> findByRequiredAgeLessThanEqual(Integer age);

    @Query("SELECT DISTINCT e FROM Extensions e JOIN e.translations t WHERE t.category = :category")
    List<Extensions> findByCategory(@Param("category") String category);

    @Query("SELECT DISTINCT e FROM Extensions e JOIN e.translations t WHERE t.distributor = :distributor")
    List<Extensions> findByDistributor(@Param("distributor") String distributor);
}

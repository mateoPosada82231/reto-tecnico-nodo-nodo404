package com.nodo.retotecnico.services;

import java.util.List;
import java.util.Optional;

import com.nodo.retotecnico.models.Extensions;

public interface ExtensionsService {

    List<Extensions> getAllExtensions();

    Optional<Extensions> getExtensionById(Integer id);

    List<Extensions> getExtensionsByCategory(String category);

    List<Extensions> getExtensionsByDistributor(String distributor);

    List<Extensions> getExtensionsForAge(Integer age);

    List<Extensions> getTrendingExtension();

    List<Extensions> getRandomExtension();

    Extensions createExtension(Extensions extension);

    Extensions updateExtension(Integer id, Extensions updatedExtension);

    void deleteExtension(Integer id);
}

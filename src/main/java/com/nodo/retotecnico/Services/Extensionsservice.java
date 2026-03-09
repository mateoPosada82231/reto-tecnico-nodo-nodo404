package com.nodo.retotecnico.Services;

import java.util.List;
import java.util.Optional;

import com.nodo.retotecnico.Models.Extensions;

public interface Extensionsservice {

    List<Extensions> getAllExtensions();

    Optional<Extensions> getExtensionById(Integer id);

    List<Extensions> getExtensionsByCategory(String category);

    List<Extensions> getExtensionsByDistributor(String distributor);

    List<Extensions> getExtensionsForAge(Integer age);

    Extensions createExtension(Extensions extension);

    Extensions updateExtension(Integer id, Extensions updatedExtension);

    void deleteExtension(Integer id);
}


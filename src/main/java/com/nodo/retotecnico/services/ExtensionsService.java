package com.nodo.retotecnico.services;

import java.util.List;
import java.util.Optional;

import com.nodo.retotecnico.dto.ExtensionResponseDTO;
import com.nodo.retotecnico.models.Extensions;

public interface ExtensionsService {

    List<ExtensionResponseDTO> getAllExtensions(String language);

    Optional<ExtensionResponseDTO> getExtensionById(Integer id, String language);

    List<ExtensionResponseDTO> getExtensionsByCategory(String category, String language);

    List<ExtensionResponseDTO> getExtensionsByDistributor(String distributor, String language);

    List<ExtensionResponseDTO> getExtensionsForAge(Integer age, String language);

    List<ExtensionResponseDTO> getTrendingExtension(String language);

    List<ExtensionResponseDTO> getRandomExtension(String language);

    Extensions createExtension(Extensions extension);

    Extensions updateExtension(Integer id, Extensions updatedExtension);

    void deleteExtension(Integer id);

    ExtensionResponseDTO toDto(Extensions extension, String language);
}

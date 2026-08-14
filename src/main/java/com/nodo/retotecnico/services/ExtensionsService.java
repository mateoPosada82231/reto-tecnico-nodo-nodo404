package com.nodo.retotecnico.services;

import java.util.List;
import java.util.Optional;

import com.nodo.retotecnico.dto.ExtensionResponseDTO;
import com.nodo.retotecnico.models.ExtensionTranslation;
import com.nodo.retotecnico.models.Extensions;

public interface ExtensionsService {

    static String buildSearchText(Extensions extension) {
        if (extension == null || extension.getTranslations() == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (ExtensionTranslation t : extension.getTranslations()) {
            appendSearchField(sb, t.getName());
            appendSearchField(sb, t.getCategory());
            appendSearchField(sb, t.getDistributor());
            appendSearchField(sb, t.getAboutGame());
        }
        return sb.toString();
    }

    static void appendSearchField(StringBuilder sb, String value) {
        if (value != null && !value.isBlank()) {
            sb.append(' ').append(value);
        }
    }

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

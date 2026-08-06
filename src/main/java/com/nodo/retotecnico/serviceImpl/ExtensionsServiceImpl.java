package com.nodo.retotecnico.serviceImpl;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nodo.retotecnico.dto.ExtensionResponseDTO;
import com.nodo.retotecnico.models.ExtensionTranslation;
import com.nodo.retotecnico.models.Extensions;
import com.nodo.retotecnico.repositories.ExtensionsRepository;
import com.nodo.retotecnico.services.ExtensionsService;

@Service
public class ExtensionsServiceImpl implements ExtensionsService {

    private static final String DEFAULT_LANG = "es";

    private final ExtensionsRepository extensionsRepository;

    public ExtensionsServiceImpl(ExtensionsRepository extensionsRepository) {
        this.extensionsRepository = extensionsRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExtensionResponseDTO> getAllExtensions(String language) {
        return extensionsRepository.findAll().stream()
                .map(e -> toDto(e, language))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ExtensionResponseDTO> getExtensionById(Integer id, String language) {
        return extensionsRepository.findById(id).map(e -> toDto(e, language));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExtensionResponseDTO> getExtensionsByCategory(String category, String language) {
        return extensionsRepository.findByCategory(category).stream()
                .map(e -> toDto(e, language))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExtensionResponseDTO> getExtensionsByDistributor(String distributor, String language) {
        return extensionsRepository.findByDistributor(distributor).stream()
                .map(e -> toDto(e, language))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExtensionResponseDTO> getExtensionsForAge(Integer age, String language) {
        return extensionsRepository.findByRequiredAgeLessThanEqual(age).stream()
                .map(e -> toDto(e, language))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExtensionResponseDTO> getTrendingExtension(String language) {
        List<Extensions> extensions = extensionsRepository.findAll();
        Extensions trending = extensions.stream()
                .max(Comparator.comparingInt(e -> e.getBuys() != null ? e.getBuys().size() : 0))
                .orElse(null);
        return trending != null ? List.of(toDto(trending, language)) : List.of();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExtensionResponseDTO> getRandomExtension(String language) {
        List<Extensions> extensions = extensionsRepository.findAll();
        if (extensions.isEmpty()) {
            return List.of();
        }
        int randomIndex = (int) (Math.random() * extensions.size());
        return List.of(toDto(extensions.get(randomIndex), language));
    }

    @Override
    @Transactional
    public Extensions createExtension(Extensions extension) {
        return extensionsRepository.save(extension);
    }

    @Override
    @Transactional
    public Extensions updateExtension(Integer id, Extensions updatedExtension) {
        Extensions existing = extensionsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Extension not found: " + id));
        existing.setPrice(updatedExtension.getPrice());
        existing.setRequiredAge(updatedExtension.getRequiredAge());
        existing.setPublicationDate(updatedExtension.getPublicationDate());
        existing.setImage(updatedExtension.getImage());
        if (updatedExtension.getTranslations() != null) {
            existing.getTranslations().clear();
            for (ExtensionTranslation t : updatedExtension.getTranslations()) {
                t.setExtension(existing);
                existing.getTranslations().add(t);
            }
        }
        return extensionsRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteExtension(Integer id) {
        if (!extensionsRepository.existsById(id)) {
            throw new RuntimeException("Extension not found: " + id);
        }
        extensionsRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ExtensionResponseDTO toDto(Extensions extension, String language) {
        String lang = (language == null || language.isBlank()) ? DEFAULT_LANG : language;
        ExtensionTranslation t = resolveTranslation(extension, lang);
        return new ExtensionResponseDTO(
                extension.getId(),
                t != null ? t.getName() : null,
                t != null ? t.getAboutGame() : null,
                t != null ? t.getCategory() : null,
                t != null ? t.getPlatforms() : null,
                t != null ? t.getLanguages() : null,
                t != null ? t.getDistributor() : null,
                extension.getPrice(),
                extension.getRequiredAge(),
                extension.getPublicationDate(),
                extension.getImage(),
                t != null ? t.getLanguage() : lang);
    }

    private ExtensionTranslation resolveTranslation(Extensions extension, String lang) {
        if (extension.getTranslations() == null || extension.getTranslations().isEmpty()) {
            return null;
        }
        Optional<ExtensionTranslation> exact = extension.getTranslations().stream()
                .filter(t -> lang.equalsIgnoreCase(t.getLanguage()))
                .findFirst();
        if (exact.isPresent()) {
            return exact.get();
        }
        return extension.getTranslations().stream()
                .filter(t -> DEFAULT_LANG.equalsIgnoreCase(t.getLanguage()))
                .findFirst()
                .orElse(extension.getTranslations().get(0));
    }
}

package com.nodo.retotecnico.Services;

import com.nodo.retotecnico.Models.Extensions;
import com.nodo.retotecnico.Repositories.ExtensionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class ExtensionsService {

    private final ExtensionsRepository extensionsRepository;

    public List<Extensions> getAllExtensions() {
        return extensionsRepository.findAll();
    }

    public Optional<Extensions> getExtensionById(Integer id) {
        return extensionsRepository.findById(id);
    }

    public List<Extensions> getExtensionsByCategory(String category) {
        return extensionsRepository.findByCategory(category);
    }

    public List<Extensions> getExtensionsByDistributor(String distributor) {
        return extensionsRepository.findByDistributor(distributor);
    }

    public List<Extensions> getExtensionsForAge(Integer age) {
        return extensionsRepository.findByRequiredAgeLessThanEqual(age);
    }

    public Extensions createExtension(Extensions extension) {
        return extensionsRepository.save(extension);
    }

    public Extensions updateExtension(Integer id, Extensions updatedExtension) {
        Extensions existing = extensionsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Extension no encontrada con id: " + id));

        existing.setName(updatedExtension.getName());
        existing.setPrice(updatedExtension.getPrice());
        existing.setRequiredAge(updatedExtension.getRequiredAge());
        existing.setAboutGame(updatedExtension.getAboutGame());
        existing.setPlatforms(updatedExtension.getPlatforms());
        existing.setLanguages(updatedExtension.getLanguages());
        existing.setDistributor(updatedExtension.getDistributor());
        existing.setPublicationDate(updatedExtension.getPublicationDate());
        existing.setCategory(updatedExtension.getCategory());

        return extensionsRepository.save(existing);
    }

    public void deleteExtension(Integer id) {
        if (!extensionsRepository.existsById(id)) {
            throw new RuntimeException("Extension no encontrada con id: " + id);
        }
        extensionsRepository.deleteById(id);
    }
}
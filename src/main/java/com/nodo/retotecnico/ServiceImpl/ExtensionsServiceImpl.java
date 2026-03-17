package com.nodo.retotecnico.ServiceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nodo.retotecnico.Models.Extensions;
import com.nodo.retotecnico.Repositories.Extensionsrepository;
import com.nodo.retotecnico.Services.ExtensionsService;

@Service
public class ExtensionsServiceImpl implements ExtensionsService {

    private final Extensionsrepository extensionsRepository;

    public ExtensionsServiceImpl(Extensionsrepository extensionsRepository) {
        this.extensionsRepository = extensionsRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Extensions> getAllExtensions() {
        return extensionsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Extensions> getExtensionById(Integer id) {
        return extensionsRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Extensions> getExtensionsByCategory(String category) {
        return extensionsRepository.findByCategory(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Extensions> getExtensionsByDistributor(String distributor) {
        return extensionsRepository.findByDistributor(distributor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Extensions> getExtensionsForAge(Integer age) {
        return extensionsRepository.findByRequiredAgeLessThanEqual(age);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Extensions> getTrendingExtension() {
        List<Extensions> extensions = extensionsRepository.findAll();
        // busca la extension con mas compras
        Extensions trending = null;
        int maxBuys = 0;
        for (Extensions extension : extensions) {
            int buysCount = extension.getBuys().size();
            if (buysCount > maxBuys) {
                maxBuys = buysCount;
                trending = extension;
            }
        }
        return trending != null ? List.of(trending) : List.of();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Extensions> getRandomExtension() {
        List<Extensions> extensions = extensionsRepository.findAll();
        if (extensions.isEmpty()) {
            return List.of();
        }
        int randomIndex = (int) (Math.random() * extensions.size());
        return List.of(extensions.get(randomIndex));
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

    @Override
    @Transactional
    public void deleteExtension(Integer id) {
        if (!extensionsRepository.existsById(id)) {
            throw new RuntimeException("Extension not found: " + id);
        }
        extensionsRepository.deleteById(id);
    }
}
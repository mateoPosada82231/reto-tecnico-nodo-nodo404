package com.nodo.retotecnico.ServiceImpl.ExtensionsServiceImpl;


import com.nodo.retotecnico.Models.Extensions;
import com.nodo.retotecnico.Repositories.Extensionsrepository;
import com.nodo.retotecnico.Services.Extensionsservice;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ExtensionsServiceImpl implements Extensionsservice {

    private final ExtensionsRepository extensionsRepository;

    public ExtensionsServiceImpl(ExtensionsRepository extensionsRepository) {
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
    @Transactional
    public Extensions createExtension(Extensions extension) {

        return extensionsRepository.save(extension);
    }

    @Override
    @Transactional
    public Extensions updateExtension(Integer id, Extensions updatedExtension) {
        Extensions existing = extensionsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("id no encontrado : " + id));

        existing.setName(updatedExtension.getName());
        existing.setCategory(updatedExtension.getCategory());
        existing.setDistributor(updatedExtension.getDistributor());
        existing.setMinAge(updatedExtension.getMinAge());
        existing.setMaxAge(updatedExtension.getMaxAge());

        return extensionsRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteExtension(Integer id) {
        if (!extensionsRepository.existsById(id)) {
            throw new RuntimeException("id no encontrado : " + id);
        }
        extensionsRepository.deleteById(id);
    }
}
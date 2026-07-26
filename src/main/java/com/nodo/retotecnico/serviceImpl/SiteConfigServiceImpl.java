package com.nodo.retotecnico.serviceImpl;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nodo.retotecnico.models.SiteConfig;
import com.nodo.retotecnico.repositories.SiteConfigRepository;
import com.nodo.retotecnico.services.SiteConfigService;

@Service
public class SiteConfigServiceImpl implements SiteConfigService {

    private final SiteConfigRepository siteConfigRepository;

    public SiteConfigServiceImpl(SiteConfigRepository siteConfigRepository) {
        this.siteConfigRepository = siteConfigRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SiteConfig> getByConfigKey(String configKey) {
        return siteConfigRepository.findByConfigKey(configKey);
    }

    @Override
    @Transactional
    public SiteConfig create(SiteConfig config) {
        if (siteConfigRepository.existsByConfigKey(config.getConfigKey())) {
            throw new RuntimeException("Config already exists for key=" + config.getConfigKey());
        }
        return siteConfigRepository.save(config);
    }

    @Override
    @Transactional
    public SiteConfig update(Long id, SiteConfig updatedConfig) {
        SiteConfig existing = siteConfigRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Config not found: " + id));
        existing.setConfigKey(updatedConfig.getConfigKey());
        existing.setConfigValue(updatedConfig.getConfigValue());
        return siteConfigRepository.save(existing);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!siteConfigRepository.existsById(id)) {
            throw new RuntimeException("Config not found: " + id);
        }
        siteConfigRepository.deleteById(id);
    }
}

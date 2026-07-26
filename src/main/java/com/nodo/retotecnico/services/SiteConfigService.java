package com.nodo.retotecnico.services;

import java.util.Optional;

import com.nodo.retotecnico.models.SiteConfig;

public interface SiteConfigService {

    Optional<SiteConfig> getByConfigKey(String configKey);

    SiteConfig create(SiteConfig config);

    SiteConfig update(Long id, SiteConfig updatedConfig);

    void delete(Long id);
}

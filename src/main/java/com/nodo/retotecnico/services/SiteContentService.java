package com.nodo.retotecnico.services;

import java.util.List;
import java.util.Optional;

import com.nodo.retotecnico.models.SiteContent;

public interface SiteContentService {

    List<SiteContent> getBySectionKey(String sectionKey, String language);

    Optional<SiteContent> getBySectionKeyAndContentKey(String sectionKey, String contentKey, String language);

    SiteContent create(SiteContent content);

    SiteContent update(Long id, SiteContent updatedContent);

    void delete(Long id);
}

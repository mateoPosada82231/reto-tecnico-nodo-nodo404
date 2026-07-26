package com.nodo.retotecnico.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nodo.retotecnico.models.SiteContent;
import com.nodo.retotecnico.repositories.SiteContentRepository;
import com.nodo.retotecnico.services.SiteContentService;

@Service
public class SiteContentServiceImpl implements SiteContentService {

    private final SiteContentRepository siteContentRepository;

    public SiteContentServiceImpl(SiteContentRepository siteContentRepository) {
        this.siteContentRepository = siteContentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SiteContent> getBySectionKey(String sectionKey, String language) {
        return siteContentRepository.findBySectionKeyAndLanguage(sectionKey, language);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SiteContent> getBySectionKeyAndContentKey(String sectionKey, String contentKey, String language) {
        return siteContentRepository.findBySectionKeyAndContentKeyAndLanguage(sectionKey, contentKey, language);
    }

    @Override
    @Transactional
    public SiteContent create(SiteContent content) {
        if (siteContentRepository.existsBySectionKeyAndContentKeyAndLanguage(
                content.getSectionKey(), content.getContentKey(), content.getLanguage())) {
            throw new RuntimeException(
                    "Content already exists for section=" + content.getSectionKey()
                            + ", key=" + content.getContentKey()
                            + ", lang=" + content.getLanguage());
        }
        return siteContentRepository.save(content);
    }

    @Override
    @Transactional
    public SiteContent update(Long id, SiteContent updatedContent) {
        SiteContent existing = siteContentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Content not found: " + id));
        existing.setSectionKey(updatedContent.getSectionKey());
        existing.setContentKey(updatedContent.getContentKey());
        existing.setContentValue(updatedContent.getContentValue());
        existing.setContentType(updatedContent.getContentType());
        existing.setLanguage(updatedContent.getLanguage());
        return siteContentRepository.save(existing);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!siteContentRepository.existsById(id)) {
            throw new RuntimeException("Content not found: " + id);
        }
        siteContentRepository.deleteById(id);
    }
}

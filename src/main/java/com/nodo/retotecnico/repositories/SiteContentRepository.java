package com.nodo.retotecnico.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nodo.retotecnico.models.SiteContent;

@Repository
public interface SiteContentRepository extends JpaRepository<SiteContent, Long> {

    List<SiteContent> findBySectionKeyAndLanguage(String sectionKey, String language);

    Optional<SiteContent> findBySectionKeyAndContentKeyAndLanguage(String sectionKey, String contentKey,
            String language);

    boolean existsBySectionKeyAndContentKeyAndLanguage(String sectionKey, String contentKey, String language);
}

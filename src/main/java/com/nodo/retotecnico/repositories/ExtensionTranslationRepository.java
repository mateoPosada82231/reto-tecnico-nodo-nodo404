package com.nodo.retotecnico.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nodo.retotecnico.models.ExtensionTranslation;

@Repository
public interface ExtensionTranslationRepository extends JpaRepository<ExtensionTranslation, Long> {

    List<ExtensionTranslation> findByExtensionId(Integer extensionId);

    Optional<ExtensionTranslation> findByExtensionIdAndLanguage(Integer extensionId, String language);

    boolean existsByExtensionIdAndLanguage(Integer extensionId, String language);
}

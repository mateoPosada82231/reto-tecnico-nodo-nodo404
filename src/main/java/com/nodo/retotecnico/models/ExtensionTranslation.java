package com.nodo.retotecnico.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "extension_translations", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "extension_id", "language" })
})
@AllArgsConstructor
@NoArgsConstructor
public class ExtensionTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "extension_id", nullable = false)
    private Extensions extension;

    @Column(name = "language", nullable = false, length = 5)
    private String language;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "about_game", nullable = false, columnDefinition = "TEXT")
    private String aboutGame;

    @Column(name = "category", length = 100)
    private String category;

    @Column(name = "platforms", length = 255)
    private String platforms;

    @Column(name = "languages", length = 255)
    private String languages;

    @Column(name = "distributor", length = 150)
    private String distributor;
}

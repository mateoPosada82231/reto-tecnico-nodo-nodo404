package com.nodo.retotecnico.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Table(name = "extensions")
@AllArgsConstructor
@NoArgsConstructor
public class Extensions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "required_age")
    private Integer requiredAge;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @Column(name = "image")
    private String image;

    @Column(name = "is_public", nullable = false)
    private boolean isPublic = false;

    @JsonProperty("isPublic")
    public boolean isPublic() {
        return isPublic;
    }

    @JsonProperty("isPublic")
    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "extension", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({ "extension", "hibernateLazyInitializer", "handler" })
    private List<ExtensionTranslation> translations = new ArrayList<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "extension")
    @JsonIgnoreProperties({ "user", "extension", "hibernateLazyInitializer", "handler" })
    private List<Buys> buys = new ArrayList<>();

    @Transient
    public String getName() {
        return pickTranslation("name");
    }

    @Transient
    public String getAboutGame() {
        return pickTranslation("aboutGame");
    }

    @Transient
    public String getCategory() {
        return pickTranslation("category");
    }

    @Transient
    public String getPlatforms() {
        return pickTranslation("platforms");
    }

    @Transient
    public String getLanguages() {
        return pickTranslation("languages");
    }

    @Transient
    public String getDistributor() {
        return pickTranslation("distributor");
    }

    private String pickTranslation(String field) {
        if (translations == null || translations.isEmpty()) {
            return null;
        }
        ExtensionTranslation first = translations.stream()
                .filter(t -> "es".equalsIgnoreCase(t.getLanguage()))
                .findFirst()
                .orElse(translations.get(0));
        return switch (field) {
            case "name" -> first.getName();
            case "aboutGame" -> first.getAboutGame();
            case "category" -> first.getCategory();
            case "platforms" -> first.getPlatforms();
            case "languages" -> first.getLanguages();
            case "distributor" -> first.getDistributor();
            default -> null;
        };
    }
}

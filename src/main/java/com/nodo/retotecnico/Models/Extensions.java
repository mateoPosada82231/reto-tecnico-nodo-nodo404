package com.nodo.retotecnico.Models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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

    @Column(name = "name")
    private String name;

    @Column(name = "about_game")
    private String aboutGame;

    @Column(name = "platforms")
    private String platforms;

    @Column(name = "languages")
    private String languages;

    @Column(name = "distributor")
    private String distributor;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @Column(name = "category")
    private String category;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "extension")
    private List<Buys> purchases = new ArrayList<>();
}

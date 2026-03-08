package com.nodo.retotecnico.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Extensions", schema = "nodo_eafit")
@AllArgsConstructor
@NoArgsConstructor

public class Extensions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)



    @Column(name = "required_age")
    private int requiredAge;

    @Column (name = "buys")
    private int buys;

    @Column (name = "name")
    private int name;

    @Column (name = "about_game")
    private String aboutGame;


    @Column (name = "platforms")
    private String platforms;


    @Column (name = "languages")
    private String languages;


    @Column (name = "distributor")
    private String distributor;


    @Column (name = "Publication_date")
    private String publicationDate;


    @Column (name = "Category")
    private String Category;



}

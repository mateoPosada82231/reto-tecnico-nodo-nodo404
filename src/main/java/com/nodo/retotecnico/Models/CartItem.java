package com.nodo.retotecnico.models;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "cart_items")
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_email", referencedColumnName = "email", nullable = false)
    @JsonIgnoreProperties({"buys", "password", "hibernateLazyInitializer", "handler"})
    private com.nodo.retotecnico.models.Users user;

    @ManyToOne
    @JoinColumn(name = "extension_id", referencedColumnName = "id", nullable = false)
    @JsonIgnoreProperties({"buys", "hibernateLazyInitializer", "handler"})
    private com.nodo.retotecnico.models.Extensions extension;

    private String language;

    private String platform;

    @Column(name = "added_date", nullable = false)
    private LocalDate addedDate;
}
package com.nodo.retotecnico.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Buys", schema = "nodo_eafit")
@AllArgsConstructor
@NoArgsConstructor

public class Buys {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String apellido;



    @Column (name = "email_users")
    private String emailUsers;


    @Column (name = "id_extensions")
    private String idExtensions;



    @Column (name = "date")
    private int date;

    @Column (name = "payment_method")
    private String paymentMethod;


}

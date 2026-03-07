package com.nodo.retotecnico.Models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "Users", schema = "nodo_eafit")
@AllArgsConstructor
@NoArgsConstructor

public class Users {

    private String nombre;
    private String apellido;


    @Id
    @Column(name = "email")
    private String email;


    @Column(name = "country")
    private String country;


    @Column(name = "date_of_birth")
    private String DateOfBirth;


    @Column(name = "identificacion")
    private String identificacion;


    @Column(name = "full_name")
    private String FullName;


    @Column(name = "Mobile number")
    private String MobileNumber;


    @Column(name = "date_of_admission")
    private String DateOfAdmission;


    // Relación con la tabla Buys
    @ManyToOne
    @JoinColumn(name = "buy_id")
    private Buys buys;

    // Relación con la tabla Extensions
    @ManyToOne
    @JoinColumn(name = "extension_id")
    private Extensions extensions;


}

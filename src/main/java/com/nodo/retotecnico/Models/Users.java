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

    @Id
    @Column(name = "email")
    private String email;


    @Column(name = "country")
    private String country;


    @Column(name = "date_of_birth")
    private String dateOfBirth;


    @Column(name = "identificacion")
    private String identificacion;


    @Column(name = "full_name")
    private String fullName;


    @Column(name = "mobile_number")
    private String mombileNumber;


    @Column(name = "date_of_admission")
    private String dateOfAdmission;

    @Column(name = "password")
    private int password;

}

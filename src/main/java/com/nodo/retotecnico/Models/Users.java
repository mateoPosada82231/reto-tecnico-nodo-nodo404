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
    private String DateOfBirth;


    @Column(name = "identificacion")
    private String identificacion;


    @Column(name = "full_name")
    private String FullName;


    @Column(name = "Mobile number")
    private String MobileNumber;


    @Column(name = "date_of_admission")
    private String DateOfAdmission;

    @Column(name = "password")
    private int password;

}

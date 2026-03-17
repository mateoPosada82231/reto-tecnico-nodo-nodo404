package com.nodo.retotecnico.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String country;
    private String identification;
    private String fullName;
    private String mobileNumber;
    private LocalDate dateOfBirth;
}
package com.nodo.retotecnico.dto;

import com.nodo.retotecnico.models.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class UserResponseDTO {
    private String email;
    private String fullName;
    private AuthProvider provider;
    private String providerId;
    private String country;
    private String identification;
    private String mobileNumber;
    private LocalDate dateOfBirth;
    private boolean profileComplete;
    private boolean betaTester;
    private boolean admin;
}

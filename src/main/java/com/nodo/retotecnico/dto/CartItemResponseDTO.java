package com.nodo.retotecnico.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponseDTO {
    private Integer id;
    private LocalDate addedDate;
    private String language;
    private String platform;
    private String userEmail;
    private ExtensionResponseDTO extension;
}

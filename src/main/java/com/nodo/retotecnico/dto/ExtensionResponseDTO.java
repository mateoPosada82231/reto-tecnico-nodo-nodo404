package com.nodo.retotecnico.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExtensionResponseDTO {
    private Integer id;
    private String name;
    private String aboutGame;
    private String category;
    private String platforms;
    private String languages;
    private String distributor;
    private BigDecimal price;
    private Integer requiredAge;
    private LocalDate publicationDate;
    private String image;
    private boolean isPublic;
    private String language;
    private String searchText;

    @JsonProperty("isPublic")
    public boolean isPublic() {
        return isPublic;
    }
}

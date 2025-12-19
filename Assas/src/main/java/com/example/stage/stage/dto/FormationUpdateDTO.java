package com.example.stage.stage.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO pour mettre à jour une formation
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormationUpdateDTO {

    @Size(max = 500, message = "L'URL de l'image française ne peut pas dépasser 500 caractères")
    private String imageFrancais;

    @Size(max = 500, message = "L'URL de l'image arabe ne peut pas dépasser 500 caractères")
    private String imageArabe;
    private LocalDateTime endDate;

}
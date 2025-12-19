package com.example.stage.stage.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO pour supprimer un avis par email
 */
@Data
public class ReviewDeleteDTO {

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    private String clientEmail;

    private String reason; // Raison de la suppression (optionnel)
}
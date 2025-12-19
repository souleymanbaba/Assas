package com.example.stage.stage.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * DTO pour créer un avis (version simplifiée avec email direct)
 */
@Data
public class ReviewCreateDTO {

    @NotNull(message = "L'ID du professionnel est obligatoire")
    private Long professionalId;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    @Size(max = 255, message = "L'email ne peut pas dépasser 255 caractères")
    private String clientEmail;

    // Optionnel: nom du client pour l'affichage
    @Size(max = 100, message = "Le nom ne peut pas dépasser 100 caractères")
    private String clientName;

    @NotNull(message = "La note est obligatoire")
    @Min(value = 1, message = "La note doit être entre 1 et 5")
    @Max(value = 5, message = "La note doit être entre 1 et 5")
    private Integer rating;

    @Size(max = 1000, message = "Le commentaire ne peut pas dépasser 1000 caractères")
    private String comment;
}
package com.example.stage.stage.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProfessionalCreateDTO {

    @NotBlank(message = "Le prénom est obligatoire")
    @Size(min = 2, max = 50, message = "Le prénom doit contenir entre 2 et 50 caractères")
    private String firstName;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 50, message = "Le nom doit contenir entre 2 et 50 caractères")
    private String lastName;

    @NotBlank(message = "Le numéro de téléphone est obligatoire")
    @Pattern(regexp = "^[0-9+\\-\\s()]+$", message = "Format de numéro de téléphone invalide")
    private String phone;

    @Pattern(regexp = "^[0-9+\\-\\s()]*$", message = "Format de numéro WhatsApp invalide")
    private String whatsapp;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    private String email;

    @Size(max = 1000, message = "La description ne peut pas dépasser 1000 caractères")
    private String description;

    @Size(max = 200, message = "La position ne peut pas dépasser 200 caractères")
    private String position;

    @NotBlank(message = "Le service à fournir est obligatoire")
    private String serviceType;

    @NotNull(message = "La spécialité est obligatoire")
    private Long specialtyId;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$",
            message = "Le mot de passe doit contenir au moins une majuscule, une minuscule et un chiffre")
    private String password;

    @NotBlank(message = "La confirmation du mot de passe est obligatoire")
    private String confirmPassword;
}
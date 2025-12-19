package com.example.stage.stage.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CategoryCreateDTO {

    @NotBlank(message = "Le nom de la catégorie est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    private String name;

    @Size(max = 50, message = "L'icône ne peut pas dépasser 50 caractères")
    private String icon;

    private MultipartFile image; // Image optionnelle

    @Min(value = 0, message = "L'ordre d'affichage doit être positif")
    private Integer displayOrder = 0;

    private Boolean isActive = true;

    private Long parentCategoryId; // ID de la catégorie parent (optionnel)
}
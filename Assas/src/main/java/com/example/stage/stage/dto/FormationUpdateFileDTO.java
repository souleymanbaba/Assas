package com.example.stage.stage.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormationUpdateFileDTO {
    private MultipartFile imageFrancais;
    private MultipartFile imageArabe;

    // Optionnel: URLs pour compatibilité avec l'ancien système
    @Size(max = 500, message = "L'URL de l'image française ne peut pas dépasser 500 caractères")
    private String imageFrancaisUrl;

    @Size(max = 500, message = "L'URL de l'image arabe ne peut pas dépasser 500 caractères")
    private String imageArabeUrl;

    // Flags pour supprimer les images existantes
    private boolean deleteImageFrancais = false;
    private boolean deleteImageArabe = false;

    private LocalDateTime endDate;

}
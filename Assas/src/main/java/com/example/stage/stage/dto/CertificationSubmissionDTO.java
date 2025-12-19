package com.example.stage.stage.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CertificationSubmissionDTO {

    @NotNull(message = "La photo de profil est obligatoire")
    private MultipartFile profilePhoto;

    @NotNull(message = "L'image de la carte d'identité est obligatoire")
    private MultipartFile identityCard;

    @NotNull(message = "Le document de spécialisation est obligatoire")
    private MultipartFile specializationDocument;
}
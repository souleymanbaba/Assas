package com.example.stage.stage.dto;

import com.example.stage.stage.entity.Professional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalSummaryDTO {
    private Long professionalId;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String whatsapp;
    private String profilePhotoUrl;
    private String description;
    private String position;
    private String serviceType;
    private Professional.CertificationStatus certificationStatus;
    private Boolean isCertified;
    private LocalDateTime createdAt;

    // Spécialité (informations de base)
    private String specialtyName;
    private String specialtyIcon;

    // Statistiques des avis
    private Double ratingAverage;
    private Integer ratingCount;
}


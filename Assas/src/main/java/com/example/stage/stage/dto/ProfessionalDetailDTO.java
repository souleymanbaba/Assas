package com.example.stage.stage.dto;

import com.example.stage.stage.entity.Professional;
import com.example.stage.stage.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO détaillé pour le profil complet d'un professionnel (Méthode 1)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalDetailDTO {
    // Informations utilisateur
    private Long userId;
    private String email;
    private User.Role role;
    private Boolean isActive;
    private Boolean emailVerified;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;

    // Informations professionnel
    private Long professionalId;
    private String firstName;
    private String lastName;
    private String phone;
    private String whatsapp;
    private String profilePhotoUrl;
    private String description;
    private String position;
    private String serviceType;
    private Professional.CertificationStatus certificationStatus;
    private LocalDateTime professionalCreatedAt;
    private LocalDateTime professionalUpdatedAt;

    // Spécialité
    private CategoryDTO specialty;

    // Statistiques des avis
    private Double ratingAverage;
    private Integer ratingCount;
    private List<ReviewDTO> recentReviews;

    // Informations de certification (si acceptée)
    private CertificationInfoDTO certificationInfo;
}
package com.example.stage.stage.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO pour les professionnels les mieux notés
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalRatingDTO {
    private Long professionalId;
    private String professionalName;
    private Double averageRating;
    private Long reviewCount;
    private ReviewStatsDTO stats;
}
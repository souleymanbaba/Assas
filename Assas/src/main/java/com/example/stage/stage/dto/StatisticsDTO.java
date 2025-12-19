package com.example.stage.stage.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsDTO {
    private Long totalUsers;
    private Long totalProfessionals;
    private Long certifiedProfessionals;
    private Long pendingCertifications;
    private Long totalReviews;
    private Double averageRating;
    private Long activePublications;
    private Long totalFormations;
    private Long totalRegulations;
}
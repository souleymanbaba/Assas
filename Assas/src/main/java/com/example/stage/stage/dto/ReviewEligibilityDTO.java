package com.example.stage.stage.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO pour vérifier l'éligibilité à donner un avis
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewEligibilityDTO {
    private Long professionalId;
    private String email;
    private Boolean hasExistingReview;
    private Boolean canReview;
    private String message;
}
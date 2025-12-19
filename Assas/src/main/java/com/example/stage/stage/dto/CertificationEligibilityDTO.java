package com.example.stage.stage.dto;

import com.example.stage.stage.entity.Professional;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * DTO pour vérifier l'éligibilité à soumettre une demande de certification
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificationEligibilityDTO {
    private Long professionalId;
    private Professional.CertificationStatus currentStatus;
    private Boolean hasExistingRequest;
    private Boolean canSubmit;
    private String message;
}
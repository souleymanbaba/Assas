package com.example.stage.stage.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CertificationStatusUpdateDTO {
    @NotNull(message = "Le statut de certification est obligatoire")
    private String certificationStatus; // PENDING, ACCEPTED, REJECTED, REVOKED

    private String reason; // Raison du changement de statut (optionnel)
}

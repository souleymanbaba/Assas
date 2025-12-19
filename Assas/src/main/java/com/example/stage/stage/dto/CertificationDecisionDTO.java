package com.example.stage.stage.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CertificationDecisionDTO {

    @NotNull(message = "La décision est obligatoire")
    private boolean approved; // true = accepter, false = rejeter

    private String reason; // Raison du rejet ou notes d'acceptation

    private String adminNotes; // Notes administratives
}

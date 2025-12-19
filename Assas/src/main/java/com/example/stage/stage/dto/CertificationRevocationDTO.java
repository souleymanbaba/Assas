package com.example.stage.stage.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CertificationRevocationDTO {

    @NotBlank(message = "La raison de révocation est obligatoire")
    private String reason;

    private String adminNotes;
}
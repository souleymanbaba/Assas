package com.example.stage.stage.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificationActivityDTO {
    private String action; // SUBMITTED, APPROVED, REJECTED, REVOKED
    private String professionalName;
    private String matricule;
    private String adminName;
    private LocalDateTime timestamp;
    private String details;
}

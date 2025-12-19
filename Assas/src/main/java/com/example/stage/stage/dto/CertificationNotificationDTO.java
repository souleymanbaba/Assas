package com.example.stage.stage.dto;

import com.example.stage.stage.entity.Professional;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificationNotificationDTO {
    private String type; // SUBMITTED, APPROVED, REJECTED, REVOKED
    private String professionalName;
    private String professionalEmail;
    private String matricule;
    private Professional.CertificationStatus newStatus;
    private Professional.CertificationStatus oldStatus;
    private String reason;
    private LocalDateTime timestamp;
    private String adminEmail;
}

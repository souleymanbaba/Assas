package com.example.stage.stage.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificationExportDTO {
    private String matricule;
    private String professionalName;
    private String email;
    private String phone;
    private String serviceType;
    private String companyName;
    private String certificationStatus;
    private LocalDateTime submittedAt;
    private LocalDateTime validatedAt;
    private String validatedBy;
    private int documentCount;
    private String rejectionReason;
    private String city;
}

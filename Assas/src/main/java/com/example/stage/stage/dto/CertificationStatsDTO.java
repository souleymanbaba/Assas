package com.example.stage.stage.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificationStatsDTO {
    private Long totalProfessionals;
    private Long certifiedCount;
    private Long pendingCount;
    private Long rejectedCount;
    private Double certificationRate;
    private Map<String, Long> certificationsByServiceType;
    private Map<String, Long> recentActivity; // derniers 30 jours
    private Long documentsCount;
    private Double averageProcessingTime; // en jours
}

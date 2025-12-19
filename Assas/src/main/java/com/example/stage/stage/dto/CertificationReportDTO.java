package com.example.stage.stage.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificationReportDTO {
    private LocalDateTime reportDate;
    private String reportPeriod; // WEEKLY, MONTHLY, QUARTERLY, YEARLY

    // Statistiques globales
    private CertificationStatsDTO globalStats;

    // Évolution dans le temps
    private Map<String, Long> evolutionByMonth;

    // Répartition par type de service
    private Map<String, CertificationStatsDTO> statsByServiceType;

    // Top professionnels certifiés
    private List<ProfessionalDTO> topCertifiedProfessionals;

    // Temps de traitement moyen
    private Map<String, Double> averageProcessingTimeByType;

    // Taux de réussite par type de document
    private Map<String, Double> successRateByDocumentType;
}

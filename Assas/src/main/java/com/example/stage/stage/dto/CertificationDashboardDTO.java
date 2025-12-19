package com.example.stage.stage.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificationDashboardDTO {
    private CertificationStatsDTO statistics;
    private List<CertificationRequestDTO> recentRequests;
    private List<CertificationRequestDTO> urgentRequests; // > 48h en attente
    private List<CertificationActivityDTO> recentActivity;
}

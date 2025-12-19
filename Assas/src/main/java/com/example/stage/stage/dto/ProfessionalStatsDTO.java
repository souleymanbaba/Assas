package com.example.stage.stage.dto;

import lombok.Data;

@Data
public class ProfessionalStatsDTO {
    private long totalProfessionals;
    private long pendingCertifications;
    private long acceptedCertifications;
    private long rejectedCertifications;
    private long revokedCertifications;
}

package com.example.stage.stage.dto;

import com.example.stage.stage.entity.Professional;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificationStatusDTO {
    private Professional.CertificationStatus status;
    private LocalDateTime submittedAt;
    private LocalDateTime validatedAt;
    private String validatedByEmail;
    private String rejectionReason;
    private List<DocumentDTO> documents;
    private boolean canResubmit;
    private String statusMessage;
    private String nextSteps;
}

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
public class CertificationRequestDetailDTO {
    private ProfessionalDTO professional;
    private List<DocumentDTO> documents;
    private LocalDateTime submittedAt;
    private Professional.CertificationStatus status;
    private String adminComment;
    private LocalDateTime validatedAt;
    private String validatedByEmail;
}

package com.example.stage.stage.dto;

import com.example.stage.stage.entity.CertificationRequest;
import com.example.stage.stage.entity.Professional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificationInfoDTO {
    private CertificationRequest.CertificationStatus status;
    private LocalDateTime submittedAt;
    private LocalDateTime processedAt;
    private String profilePhotoUrl;
    private String identityCardUrl;
    private String specializationDocumentUrl;
    private String adminNotes;
}

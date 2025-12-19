package com.example.stage.stage.dto;

import com.example.stage.stage.entity.CertificationRequest;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CertificationResponseDTO {
    private Long id;
    private Long professionalId;
    private String professionalName;
    private String professionalEmail;
    private String profilePhotoUrl;
    private String identityCardUrl;
    private String specializationDocumentUrl;
    private String status;
    private String statusDisplay;
    private LocalDateTime submittedAt;
    private LocalDateTime processedAt;
    private String rejectionReason;
    private String adminNotes;
    private String processedByName;

    public CertificationResponseDTO(CertificationRequest request) {
        this.id = request.getId();
        this.professionalId = request.getProfessional().getId();
        this.professionalName = request.getProfessional().getFirstName() + " " + request.getProfessional().getLastName();
        this.professionalEmail = request.getProfessional().getUser().getEmail();
        this.profilePhotoUrl = request.getProfilePhotoUrl();
        this.identityCardUrl = request.getIdentityCardUrl();
        this.specializationDocumentUrl = request.getSpecializationDocumentUrl();
        this.status = request.getStatus().name();
        this.statusDisplay = request.getStatus().getDisplayName();
        this.submittedAt = request.getSubmittedAt();
        this.processedAt = request.getProcessedAt();
        this.rejectionReason = request.getRejectionReason();
        this.adminNotes = request.getAdminNotes();
    }
}

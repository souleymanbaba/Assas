package com.example.stage.stage.dto;

import com.example.stage.stage.entity.Professional;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ProfessionalResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String whatsapp;
    private String email;
    private String profilePhotoUrl; // NOUVEAU CHAMP
    private String description;
    private String position;
    private String serviceType;
    private String specialtyName;
    private Long specialtyId;
    private String certificationStatus;
    private String certificationStatusDisplay;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private CertificationResponseDTO certificationRequest; // NOUVEAU CHAMP

    public ProfessionalResponseDTO(Professional professional) {
        this.id = professional.getId();
        this.firstName = professional.getFirstName();
        this.lastName = professional.getLastName();
        this.phone = professional.getPhone();
        this.whatsapp = professional.getWhatsapp();
        this.email = professional.getUser().getEmail();
        this.profilePhotoUrl = professional.getProfilePhotoUrl(); // NOUVEAU
        this.description = professional.getDescription();
        this.position = professional.getPosition();
        this.serviceType = professional.getServiceType();
        this.specialtyName = professional.getSpecialty() != null ? professional.getSpecialty().getName() : null;
        this.specialtyId = professional.getSpecialty() != null ? professional.getSpecialty().getId() : null;
        this.certificationStatus = professional.getCertificationStatus().name();
        this.certificationStatusDisplay = professional.getCertificationStatus().getDisplayName();
        this.createdAt = professional.getCreatedAt();
        this.updatedAt = professional.getUpdatedAt();

        // Ajouter les informations de certification si elles existent
        if (professional.getCertificationRequest() != null) {
            this.certificationRequest = new CertificationResponseDTO(professional.getCertificationRequest());
        }
    }
}
// =====================================
// 1. ENTITY - CertificationRequest.java
// =====================================
package com.example.stage.stage.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "certification_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificationRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professional_id", nullable = false)
    private Professional professional;

    // Documents de certification
    @Column(name = "profile_photo_url")
    private String profilePhotoUrl;

    @Column(name = "identity_card_url")
    private String identityCardUrl;

    @Column(name = "specialization_document_url")
    private String specializationDocumentUrl;

    // Statut de la demande
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CertificationStatus status = CertificationStatus.PENDING;

    // Informations de traitement
    @Column(name = "submitted_at", nullable = false)
    private LocalDateTime submittedAt;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;

    @Column(name = "processed_by")
    private Long processedBy; // ID de l'admin qui a traité

    @Column(name = "rejection_reason")
    private String rejectionReason;

    @Column(name = "admin_notes", length = 1000)
    private String adminNotes;

    @PrePersist
    protected void onCreate() {
        submittedAt = LocalDateTime.now();
    }

    public enum CertificationStatus {
        PENDING("En attente"),
        ACCEPTED("Accepté"),
        REJECTED("Rejeté"),
        REVOKED("Révoqué");

        private final String displayName;

        CertificationStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}

package com.example.stage.stage.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "professionals")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Professional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Informations personnelles
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String phone;

    private String whatsapp;

    // Photo de profil
    @Column(name = "profile_photo_url")
    private String profilePhotoUrl;

    @Column(length = 1000)
    private String description;

    // Localisation
    private String position;

    // Service à fournir
    @Column(name = "service_type", nullable = false)
    private String serviceType;

    // Spécialité
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialty_id")
    private Category specialty;

    // Certification Status
    @Enumerated(EnumType.STRING)
    @Column(name = "certification_status", nullable = false)
    private CertificationStatus certificationStatus = CertificationStatus.PENDING;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Relations
    @OneToMany(mappedBy = "professional", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;

    @OneToOne(mappedBy = "professional", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CertificationRequest certificationRequest;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
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

package com.example.stage.stage.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "certification_activities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificationActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "professional_id", nullable = false)
    private Long professionalId;

    @Column(name = "admin_id")
    private Long adminId;

    @Column(name = "action_type", nullable = false)
    private String actionType; // SUBMITTED, APPROVED, REJECTED, REVOKED, DOCUMENT_UPLOADED

    @Column(name = "old_status")
    @Enumerated(EnumType.STRING)
    private Professional.CertificationStatus oldStatus;

    @Column(name = "new_status")
    @Enumerated(EnumType.STRING)
    private Professional.CertificationStatus newStatus;

    @Column(length = 1000)
    private String details;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
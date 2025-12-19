package com.example.stage.stage.repository;

import com.example.stage.stage.entity.CertificationRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CertificationRequestRepository extends JpaRepository<CertificationRequest, Long> {

    // Trouver par professionnel
    Optional<CertificationRequest> findByProfessionalId(Long professionalId);

    // Trouver par statut
    List<CertificationRequest> findByStatus(CertificationRequest.CertificationStatus status);

    // Trouver par statut avec pagination
    Page<CertificationRequest> findByStatus(CertificationRequest.CertificationStatus status, Pageable pageable);

    // Vérifier si une demande existe pour ce professionnel
    boolean existsByProfessionalId(Long professionalId);

    // Compter par statut
    long countByStatus(CertificationRequest.CertificationStatus status);

    // Recherche par nom du professionnel
    @Query("SELECT cr FROM CertificationRequest cr WHERE " +
            "LOWER(cr.professional.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(cr.professional.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<CertificationRequest> searchByProfessionalName(@Param("keyword") String keyword);
}
package com.example.stage.stage.repository;

import com.example.stage.stage.entity.CertificationDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CertificationDocumentRepository extends JpaRepository<CertificationDocument, Long> {

    // Trouver tous les documents d'un professionnel
    List<CertificationDocument> findByProfessionalIdOrderByUploadedAtDesc(Long professionalId);

    // Trouver par ID et professionnel (pour sécurité)
    Optional<CertificationDocument> findByIdAndProfessionalId(Long id, Long professionalId);

    // Compter les documents d'un professionnel
    Long countByProfessionalId(Long professionalId);

    // Supprimer tous les documents d'un professionnel
    @Modifying
    @Query("DELETE FROM CertificationDocument cd WHERE cd.professional.id = :professionalId")
    void deleteByProfessionalId(@Param("professionalId") Long professionalId);

    // Trouver par type de document
    List<CertificationDocument> findByDocumentType(CertificationDocument.DocumentType documentType);

    // Trouver documents uploadés dans une période
    @Query("SELECT cd FROM CertificationDocument cd WHERE cd.uploadedAt BETWEEN :startDate AND :endDate")
    List<CertificationDocument> findByUploadedAtBetween(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    // Compter documents par type
    @Query("SELECT cd.documentType, COUNT(cd) FROM CertificationDocument cd GROUP BY cd.documentType")
    List<Object[]> countByDocumentType();

    // Trouver documents orphelins (professionnel supprimé)
    @Query("SELECT cd FROM CertificationDocument cd WHERE cd.professional IS NULL")
    List<CertificationDocument> findOrphanedDocuments();
}

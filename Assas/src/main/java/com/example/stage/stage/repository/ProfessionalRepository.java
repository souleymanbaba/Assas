package com.example.stage.stage.repository;


import com.example.stage.stage.entity.Category;
import com.example.stage.stage.entity.Professional;
import com.example.stage.stage.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessionalRepository extends JpaRepository<Professional, Long> {


    Optional<Professional> findByUser(User user);
    List<Professional> findByUser_IsActiveTrue();

    @Query("SELECT p FROM Professional p WHERE p.specialty.id = :specialtyId AND p.user.isActive = true")
    List<Professional> findBySpecialtyIdAndUser_IsActiveTrue(@Param("specialtyId") Long specialtyId);

    // Compter les professionnels par spécialité (catégorie) qui sont actifs
    @Query("SELECT COUNT(p) FROM Professional p WHERE p.specialty.id = :specialtyId AND p.user.isActive = true")
    long countBySpecialtyIdAndUser_IsActiveTrue(@Param("specialtyId") Long specialtyId);

    // NOUVELLES MÉTHODES POUR LA CERTIFICATION

    // Trouver par statut de certification avec pagination
    Page<Professional> findByCertificationStatus(Professional.CertificationStatus status, Pageable pageable);

    // Compter par statut de certification


    // Trouver certifications par statut et type de service




    // Certifications en attente depuis plus de X jours
    @Query("SELECT p FROM Professional p WHERE p.certificationStatus = 'PENDING' " +
            "AND p.createdAt < :threshold")
    List<Professional> findPendingCertificationsOlderThan(@Param("threshold") LocalDateTime threshold);

    // Statistiques de certification par période


    // Professionnels certifiés par type de service
    @Query("SELECT p.serviceType, COUNT(p) FROM Professional p " +
            "WHERE p.certificationStatus = 'CERTIFIED' " +
            "GROUP BY p.serviceType")
    List<Object[]> getCertifiedCountByServiceType();



    // Professionnels avec documents manquants
    @Query("SELECT p FROM Professional p LEFT JOIN CertificationDocument cd ON p.id = cd.professional.id " + "WHERE p.certificationStatus = 'PENDING' " + "GROUP BY p.id HAVING COUNT(cd.id) = 0")
    List<Professional> findPendingWithoutDocuments();

    // Certifications par ville
    @Query("SELECT p FROM Professional p WHERE p.user.email = :email")
    Optional<Professional> findByUserEmail(@Param("email") String email);

    // Recherche par numéro de téléphone
    Optional<Professional> findByPhone(String phone);

    // Recherche par statut de certification
    List<Professional> findByCertificationStatus(Professional.CertificationStatus status);

    // Recherche par spécialité
    List<Professional> findBySpecialty(Category specialty);

    // Recherche par spécialité avec pagination
    Page<Professional> findBySpecialty(Category specialty, Pageable pageable);

    // Recherche par type de service
    List<Professional> findByServiceType(String serviceType);

    // Recherche par nom ou prénom (insensible à la casse)
    @Query("SELECT p FROM Professional p WHERE " +
            "LOWER(p.firstName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.lastName) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Professional> searchByName(@Param("keyword") String keyword);

    // Recherche avancée avec plusieurs critères
    @Query("SELECT p FROM Professional p WHERE " +
            "(:firstName IS NULL OR LOWER(p.firstName) LIKE LOWER(CONCAT('%', :firstName, '%'))) AND " +
            "(:lastName IS NULL OR LOWER(p.lastName) LIKE LOWER(CONCAT('%', :lastName, '%'))) AND " +
            "(:serviceType IS NULL OR p.serviceType = :serviceType) AND " +
            "(:specialtyId IS NULL OR p.specialty.id = :specialtyId) AND " +
            "(:certificationStatus IS NULL OR p.certificationStatus = :certificationStatus)")
    Page<Professional> searchProfessionals(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("serviceType") String serviceType,
            @Param("specialtyId") Long specialtyId,
            @Param("certificationStatus") Professional.CertificationStatus certificationStatus,
            Pageable pageable);

    // Compter par statut de certification
    long countByCertificationStatus(Professional.CertificationStatus status);

    // Vérifier si un professionnel existe par user ID
    boolean existsByUserId(Long userId);

    // Trouver par user ID
    Optional<Professional> findByUserId(Long userId);

}

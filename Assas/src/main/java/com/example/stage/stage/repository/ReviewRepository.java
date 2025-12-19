package com.example.stage.stage.repository;

import com.example.stage.stage.entity.Review;
import com.example.stage.stage.entity.Professional;
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
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // === RECHERCHES PAR PROFESSIONNEL ===

    // Recherche par professionnel
    List<Review> findByProfessionalOrderByCreatedAtDesc(Professional professional);

    // Recherche par professionnel avec pagination
    Page<Review> findByProfessionalOrderByCreatedAtDesc(Professional professional, Pageable pageable);

    // Recherche par professionnel ID
    List<Review> findByProfessionalIdOrderByCreatedAtDesc(Long professionalId);

    // Recherche par professionnel ID avec pagination
    Page<Review> findByProfessionalIdOrderByCreatedAtDesc(Long professionalId, Pageable pageable);

    // Compter les avis par professionnel
    long countByProfessionalId(Long professionalId);

    // === RECHERCHES PAR EMAIL CLIENT (NOUVELLE APPROCHE) ===

    // Recherche par email client
    List<Review> findByClientEmailOrderByCreatedAtDesc(String clientEmail);

    // Recherche par email client avec pagination
    Page<Review> findByClientEmailOrderByCreatedAtDesc(String clientEmail, Pageable pageable);

    // Recherche par email client (insensible à la casse)
    @Query("SELECT r FROM Review r WHERE LOWER(r.clientEmail) = LOWER(:clientEmail) ORDER BY r.createdAt DESC")
    List<Review> findByClientEmailIgnoreCaseOrderByCreatedAtDesc(@Param("clientEmail") String clientEmail);

    // Recherche par email client avec pagination (insensible à la casse)
    @Query("SELECT r FROM Review r WHERE LOWER(r.clientEmail) = LOWER(:clientEmail) ORDER BY r.createdAt DESC")
    Page<Review> findByClientEmailIgnoreCaseOrderByCreatedAtDesc(@Param("clientEmail") String clientEmail, Pageable pageable);

    // Compter les avis par email client
    long countByClientEmail(String clientEmail);

    // Compter les avis par email client (insensible à la casse)
    @Query("SELECT COUNT(r) FROM Review r WHERE LOWER(r.clientEmail) = LOWER(:clientEmail)")
    long countByClientEmailIgnoreCase(@Param("clientEmail") String clientEmail);

    // === VÉRIFICATIONS D'ÉLIGIBILITÉ ===

    // Vérifier si un email a déjà donné un avis à un professionnel
    boolean existsByProfessionalIdAndClientEmail(Long professionalId, String clientEmail);

    // Vérifier si un email a déjà donné un avis (insensible à la casse)
    @Query("SELECT COUNT(r) > 0 FROM Review r WHERE r.professional.id = :professionalId AND LOWER(r.clientEmail) = LOWER(:clientEmail)")
    boolean existsByProfessionalIdAndClientEmailIgnoreCase(@Param("professionalId") Long professionalId,
                                                           @Param("clientEmail") String clientEmail);

    // Trouver un avis spécifique par professionnel et email
    Optional<Review> findByProfessionalIdAndClientEmail(Long professionalId, String clientEmail);

    // Trouver un avis spécifique (insensible à la casse)
    @Query("SELECT r FROM Review r WHERE r.professional.id = :professionalId AND LOWER(r.clientEmail) = LOWER(:clientEmail)")
    Optional<Review> findByProfessionalIdAndClientEmailIgnoreCase(@Param("professionalId") Long professionalId,
                                                                  @Param("clientEmail") String clientEmail);

    // === RECHERCHES PAR RATING ===

    // Recherche par rating
    List<Review> findByRatingOrderByCreatedAtDesc(Integer rating);

    // Recherche par rating avec pagination
    Page<Review> findByRatingOrderByCreatedAtDesc(Integer rating, Pageable pageable);

    // Compter les avis par rating
    long countByRating(Integer rating);

    // === STATISTIQUES ET CALCULS ===

    // Calculer la moyenne des ratings pour un professionnel
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.professional.id = :professionalId")
    Double calculateAverageRatingByProfessionalId(@Param("professionalId") Long professionalId);

    // Obtenir la distribution des ratings pour un professionnel
    @Query("SELECT r.rating, COUNT(r) FROM Review r WHERE r.professional.id = :professionalId GROUP BY r.rating ORDER BY r.rating")
    List<Object[]> getRatingDistributionByProfessionalId(@Param("professionalId") Long professionalId);

    // Statistiques des ratings par professionnel (version alternative)
    @Query("SELECT r.rating, COUNT(r) FROM Review r WHERE r.professional.id = :professionalId GROUP BY r.rating ORDER BY r.rating DESC")
    List<Object[]> getRatingStatsByProfessionalId(@Param("professionalId") Long professionalId);

    // Obtenir les professionnels les mieux notés
    @Query("SELECT r.professional.id, AVG(r.rating) as avgRating, COUNT(r) as reviewCount " +
            "FROM Review r GROUP BY r.professional.id " +
            "HAVING COUNT(r) >= :minReviews " +
            "ORDER BY avgRating DESC, reviewCount DESC")
    List<Object[]> findTopRatedProfessionals(@Param("minReviews") long minReviews);

    // === RECHERCHE AVANCÉE (VERSION CORRIGÉE) ===

    // Recherche avancée avec filtres (utilise clientEmail au lieu de clientId)
    @Query("SELECT r FROM Review r WHERE " +
            "(:professionalId IS NULL OR r.professional.id = :professionalId) AND " +
            "(:clientEmail IS NULL OR LOWER(r.clientEmail) LIKE LOWER(CONCAT('%', :clientEmail, '%'))) AND " +
            "(:rating IS NULL OR r.rating = :rating) AND " +
            "(:startDate IS NULL OR r.createdAt >= :startDate) AND " +
            "(:endDate IS NULL OR r.createdAt <= :endDate) AND " +
            "(:comment IS NULL OR LOWER(r.comment) LIKE LOWER(CONCAT('%', :comment, '%')))")
    Page<Review> searchReviews(
            @Param("professionalId") Long professionalId,
            @Param("clientEmail") String clientEmail,
            @Param("rating") Integer rating,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("comment") String comment,
            Pageable pageable);

    // === REQUÊTES SPÉCIALES ===

    // Obtenir les meilleurs avis (rating >= 4)
    @Query("SELECT r FROM Review r WHERE r.rating >= 4 ORDER BY r.rating DESC, r.createdAt DESC")
    List<Review> findTopReviews();

    // Obtenir les meilleurs avis avec pagination
    @Query("SELECT r FROM Review r WHERE r.rating >= :minRating ORDER BY r.rating DESC, r.createdAt DESC")
    Page<Review> findTopReviews(@Param("minRating") Integer minRating, Pageable pageable);

    // Obtenir les avis récents (derniers X jours)
    @Query("SELECT r FROM Review r WHERE r.createdAt >= :since ORDER BY r.createdAt DESC")
    List<Review> findRecentReviews(@Param("since") LocalDateTime since);

    // Obtenir les avis récents avec pagination
    @Query("SELECT r FROM Review r WHERE r.createdAt >= :since ORDER BY r.createdAt DESC")
    Page<Review> findRecentReviews(@Param("since") LocalDateTime since, Pageable pageable);

    // Obtenir les avis dans une période
    @Query("SELECT r FROM Review r WHERE r.createdAt BETWEEN :startDate AND :endDate ORDER BY r.createdAt DESC")
    List<Review> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate,
                                        @Param("endDate") LocalDateTime endDate);

    // Obtenir les avis récents pour un professionnel spécifique
    @Query("SELECT r FROM Review r WHERE r.professional.id = :professionalId AND r.createdAt >= :since ORDER BY r.createdAt DESC")
    List<Review> findRecentReviewsByProfessionalId(@Param("professionalId") Long professionalId,
                                                   @Param("since") LocalDateTime since);

    // === ANALYSES ET RAPPORTS ===

    // Trouver les emails les plus actifs (qui donnent le plus d'avis)
    @Query("SELECT r.clientEmail, COUNT(r) as reviewCount " +
            "FROM Review r " +
            "GROUP BY r.clientEmail " +
            "ORDER BY reviewCount DESC")
    List<Object[]> findMostActiveReviewers();

    // Trouver les emails les plus actifs avec limite
    @Query("SELECT r.clientEmail, COUNT(r) as reviewCount " +
            "FROM Review r " +
            "GROUP BY r.clientEmail " +
            "HAVING COUNT(r) >= :minReviews " +
            "ORDER BY reviewCount DESC")
    List<Object[]> findMostActiveReviewers(@Param("minReviews") long minReviews);

    // Statistiques globales par période
    @Query("SELECT DATE(r.createdAt) as reviewDate, COUNT(r) as dailyCount, AVG(r.rating) as avgRating " +
            "FROM Review r " +
            "WHERE r.createdAt BETWEEN :startDate AND :endDate " +
            "GROUP BY DATE(r.createdAt) " +
            "ORDER BY reviewDate DESC")
    List<Object[]> getDailyReviewStats(@Param("startDate") LocalDateTime startDate,
                                       @Param("endDate") LocalDateTime endDate);

    // Compter les avis uniques par email (éviter les doublons)
    @Query("SELECT COUNT(DISTINCT r.clientEmail) FROM Review r")
    long countUniqueReviewers();

    // Compter les avis uniques par email pour un professionnel
    @Query("SELECT COUNT(DISTINCT r.clientEmail) FROM Review r WHERE r.professional.id = :professionalId")
    long countUniqueReviewersByProfessionalId(@Param("professionalId") Long professionalId);

    // === REQUÊTES DE VALIDATION ===

    // Vérifier si un professionnel a des avis
    @Query("SELECT COUNT(r) > 0 FROM Review r WHERE r.professional.id = :professionalId")
    boolean hasProfessionalAnyReviews(@Param("professionalId") Long professionalId);

    // Obtenir le dernier avis d'un professionnel
    @Query("SELECT r FROM Review r WHERE r.professional.id = :professionalId ORDER BY r.createdAt DESC LIMIT 1")
    Optional<Review> findLatestReviewByProfessionalId(@Param("professionalId") Long professionalId);

    // Obtenir le premier avis d'un professionnel
    @Query("SELECT r FROM Review r WHERE r.professional.id = :professionalId ORDER BY r.createdAt ASC LIMIT 1")
    Optional<Review> findFirstReviewByProfessionalId(@Param("professionalId") Long professionalId);
}
package com.example.stage.stage.repository;

import com.example.stage.stage.entity.Publication;
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
public interface PublicationRepository extends JpaRepository<Publication, Long> {

    // Recherche par email
    List<Publication> findByEmailOrderByCreatedAtDesc(String email);

    // Recherche par email avec pagination
    Page<Publication> findByEmailOrderByCreatedAtDesc(String email, Pageable pageable);

    // Recherche par place
    List<Publication> findByPlaceOrderByCreatedAtDesc(Integer place);

    // Recherche par place avec pagination
    Page<Publication> findByPlaceOrderByCreatedAtDesc(Integer place, Pageable pageable);

    // Recherche par titre (insensible à la casse)
    @Query("SELECT p FROM Publication p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<Publication> searchByTitle(@Param("title") String title);

    // Publications actives (date courante entre startDate et endDate)
    @Query("SELECT p FROM Publication p WHERE :currentDate BETWEEN p.startDate AND p.endDate ORDER BY p.createdAt DESC")
    List<Publication> findActivePublications(@Param("currentDate") LocalDateTime currentDate);

    // Publications actives avec pagination
    @Query("SELECT p FROM Publication p WHERE :currentDate BETWEEN p.startDate AND p.endDate ORDER BY p.createdAt DESC")
    Page<Publication> findActivePublications(@Param("currentDate") LocalDateTime currentDate, Pageable pageable);

    // Publications expirées
    @Query("SELECT p FROM Publication p WHERE p.endDate < :currentDate ORDER BY p.endDate DESC")
    List<Publication> findExpiredPublications(@Param("currentDate") LocalDateTime currentDate);

    // Publications à venir
    @Query("SELECT p FROM Publication p WHERE p.startDate > :currentDate ORDER BY p.startDate ASC")
    List<Publication> findUpcomingPublications(@Param("currentDate") LocalDateTime currentDate);

    // Recherche avancée avec filtres
    @Query("SELECT p FROM Publication p WHERE " +
            "(:title IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
            "(:email IS NULL OR LOWER(p.email) LIKE LOWER(CONCAT('%', :email, '%'))) AND " +
            "(:place IS NULL OR p.place = :place) AND " +
            "(:startDate IS NULL OR p.startDate >= :startDate) AND " +
            "(:endDate IS NULL OR p.endDate <= :endDate)")
    Page<Publication> searchPublications(
            @Param("title") String title,
            @Param("email") String email,
            @Param("place") Integer place,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);

    // Compter les publications par email
    long countByEmail(String email);

    // Compter les publications actives
    @Query("SELECT COUNT(p) FROM Publication p WHERE :currentDate BETWEEN p.startDate AND p.endDate")
    long countActivePublications(@Param("currentDate") LocalDateTime currentDate);

    // Vérifier si une publication existe dans une période donnée pour un email
    @Query("SELECT COUNT(p) > 0 FROM Publication p WHERE p.email = :email AND " +
            "((p.startDate <= :endDate AND p.endDate >= :startDate))")
    boolean existsOverlappingPublication(
            @Param("email") String email,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    // Vérifier chevauchement pour mise à jour (exclure l'ID actuel)
    @Query("SELECT COUNT(p) > 0 FROM Publication p WHERE p.email = :email AND p.id != :id AND " +
            "((p.startDate <= :endDate AND p.endDate >= :startDate))")
    boolean existsOverlappingPublicationForUpdate(
            @Param("email") String email,
            @Param("id") Long id,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
}
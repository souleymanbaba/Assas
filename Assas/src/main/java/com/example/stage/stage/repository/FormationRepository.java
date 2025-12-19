package com.example.stage.stage.repository;

import com.example.stage.stage.entity.Formation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FormationRepository extends JpaRepository<Formation, Long> {

    // Rechercher toutes les formations triées par date de création
    List<Formation> findAllByOrderByCreatedAtDesc();

    // Rechercher toutes les formations avec pagination
    Page<Formation> findAllByOrderByCreatedAtDesc(Pageable pageable);

    // Rechercher les formations qui ont une image française
    List<Formation> findByImageFrancaisIsNotNullOrderByCreatedAtDesc();

    // Rechercher les formations qui ont une image arabe
    List<Formation> findByImageArabeIsNotNullOrderByCreatedAtDesc();

    // Rechercher les formations qui ont les deux images
    @Query("SELECT f FROM Formation f WHERE f.imageFrancais IS NOT NULL AND f.imageArabe IS NOT NULL ORDER BY f.createdAt DESC")
    List<Formation> findFormationsWithBothImages();

    // Rechercher les formations qui ont au moins une image
    @Query("SELECT f FROM Formation f WHERE f.imageFrancais IS NOT NULL OR f.imageArabe IS NOT NULL ORDER BY f.createdAt DESC")
    List<Formation> findFormationsWithAtLeastOneImage();

    // Rechercher les formations sans aucune image
    @Query("SELECT f FROM Formation f WHERE f.imageFrancais IS NULL AND f.imageArabe IS NULL ORDER BY f.createdAt DESC")
    List<Formation> findFormationsWithoutImages();

    // Compter le nombre total de formations
    long count();

    // Compter les formations avec image française
    long countByImageFrancaisIsNotNull();

    // Compter les formations avec image arabe
    long countByImageArabeIsNotNull();

    // Compter les formations avec les deux images
    @Query("SELECT COUNT(f) FROM Formation f WHERE f.imageFrancais IS NOT NULL AND f.imageArabe IS NOT NULL")
    long countFormationsWithBothImages();


    List<Formation> findByEndDateIsNullOrEndDateAfterOrderByCreatedAtDesc(LocalDateTime now);

}
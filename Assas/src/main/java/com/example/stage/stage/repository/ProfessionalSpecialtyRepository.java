package com.example.stage.stage.repository;

import com.example.stage.stage.entity.ProfessionalSpecialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessionalSpecialtyRepository extends JpaRepository<ProfessionalSpecialty, Long> {
    List<ProfessionalSpecialty> findByCategoryId(Long categoryId);

    long countByCategoryId(Long categoryId);

    // Nouvelle méthode pour récupérer les professionnels de plusieurs catégories
    List<ProfessionalSpecialty> findByCategoryIdIn(List<Long> categoryIds);

    // Autres méthodes utiles
    List<ProfessionalSpecialty> findByProfessionalId(Long professionalId);

    boolean existsByProfessionalIdAndCategoryId(Long professionalId, Long categoryId);

    void deleteByProfessionalIdAndCategoryId(Long professionalId, Long categoryId);




    /**
     * Compter le nombre de spécialités d'un professionnel
     */
    long countByProfessionalId(Long professionalId);

    /**
     * Supprimer toutes les spécialités d'un professionnel
     */
    void deleteByProfessionalId(Long professionalId);

    /**
     * Supprimer toutes les spécialités d'une catégorie
     */
    void deleteByCategoryId(Long categoryId);


}

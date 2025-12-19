package com.example.stage.stage.repository;

import com.example.stage.stage.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Recherche par nom (insensible à la casse)
    Optional<Category> findByNameIgnoreCase(String name);

    // Vérifier si le nom existe déjà
    boolean existsByNameIgnoreCase(String name);

    // Vérifier si le nom existe pour un autre ID (pour les mises à jour)
    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

    // Récupérer les catégories actives seulement
    List<Category> findByIsActiveTrueOrderByDisplayOrder();

    // Récupérer les catégories actives avec pagination
    Page<Category> findByIsActiveTrue(Pageable pageable);

    // Récupérer les catégories principales (sans parent)
    List<Category> findByParentCategoryIsNullOrderByDisplayOrder();

    // Récupérer les catégories principales actives
    List<Category> findByParentCategoryIsNullAndIsActiveTrueOrderByDisplayOrder();

    // Récupérer les sous-catégories d'une catégorie parent
    List<Category> findByParentCategoryIdOrderByDisplayOrder(Long parentId);

    // Récupérer les sous-catégories actives d'une catégorie parent
    List<Category> findByParentCategoryIdAndIsActiveTrueOrderByDisplayOrder(Long parentId);

    // Recherche par nom avec support des caractères partiels
    @Query("SELECT c FROM Category c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Category> searchByName(@Param("name") String name);

    // Recherche avancée avec filtres
    @Query("SELECT c FROM Category c WHERE " +
            "(:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:isActive IS NULL OR c.isActive = :isActive) AND " +
            "(:parentId IS NULL OR c.parentCategory.id = :parentId) AND " +
            "(:isMainCategory IS NULL OR " +
            "  (:isMainCategory = true AND c.parentCategory IS NULL) OR " +
            "  (:isMainCategory = false AND c.parentCategory IS NOT NULL))")
    Page<Category> searchCategories(
            @Param("name") String name,
            @Param("isActive") Boolean isActive,
            @Param("parentId") Long parentId,
            @Param("isMainCategory") Boolean isMainCategory,
            Pageable pageable);

    // Compter les sous-catégories d'une catégorie
    long countByParentCategoryId(Long parentId);

    // Compter les catégories actives
    long countByIsActiveTrue();

    // Trouver le prochain ordre d'affichage
    @Query("SELECT COALESCE(MAX(c.displayOrder), 0) + 1 FROM Category c WHERE c.parentCategory.id = :parentId")
    Integer findNextDisplayOrderByParentId(@Param("parentId") Long parentId);

    @Query("SELECT COALESCE(MAX(c.displayOrder), 0) + 1 FROM Category c WHERE c.parentCategory IS NULL")
    Integer findNextDisplayOrderForMainCategories();
}
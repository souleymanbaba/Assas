package com.example.stage.stage.controller;

import com.example.stage.stage.dto.*;
import com.example.stage.stage.response.ApiResponse;
import com.example.stage.stage.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CategoryController {

    private final CategoryService categoryService;


    @GetMapping("/hierarchy")
    public ResponseEntity<ApiResponse<List<CategoryHierarchyDTO>>> getCategoryHierarchy() {
        ApiResponse<List<CategoryHierarchyDTO>> response = categoryService.getCategoryHierarchy();
        return ResponseEntity.ok(response);
    }

    /**
     * API pour obtenir la hiérarchie simplifiée (ID + nom seulement)
     * GET /api/categories/hierarchy/simple
     */
    @GetMapping("/hierarchy/simple")
    public ResponseEntity<ApiResponse<List<CategorySimpleHierarchyDTO>>> getSimpleCategoryHierarchy() {
        ApiResponse<List<CategorySimpleHierarchyDTO>> response = categoryService.getSimpleCategoryHierarchy();
        return ResponseEntity.ok(response);
    }

    /**
     * API pour obtenir seulement les catégories principales (sans sous-catégories)
     * GET /api/categories/main-only
     */
    @GetMapping("/main-only")
    public ResponseEntity<ApiResponse<List<CategoryMainOnlyDTO>>> getMainCategoriesOnly() {
        ApiResponse<List<CategoryMainOnlyDTO>> response = categoryService.getMainCategoriesOnly();
        return ResponseEntity.ok(response);
    }

    // CREATE - Créer une nouvelle catégorie (Admin seulement)
    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> createCategory(
            @Valid @ModelAttribute CategoryCreateDTO request) {

        ApiResponse<CategoryResponseDTO> response = categoryService.createCategory(request);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST)
                .body(response);
    }

    // READ - Obtenir toutes les catégories avec pagination
    @GetMapping
    public ResponseEntity<ApiResponse<Page<CategoryResponseDTO>>> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "displayOrder") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {

        ApiResponse<Page<CategoryResponseDTO>> response =
                categoryService.getAllCategories(page, size, sortBy, sortDirection);

        return ResponseEntity.ok(response);
    }

    // READ - Obtenir une catégorie par ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> getCategoryById(@PathVariable Long id) {
        ApiResponse<CategoryResponseDTO> response = categoryService.getCategoryById(id);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(response);
    }

    // READ - Obtenir les catégories principales seulement
    @GetMapping("/main")
    public ResponseEntity<ApiResponse<List<CategoryResponseDTO>>> getMainCategories() {
        ApiResponse<List<CategoryResponseDTO>> response = categoryService.getMainCategories();
        return ResponseEntity.ok(response);
    }

    // READ - Obtenir les sous-catégories d'une catégorie
    @GetMapping("/{parentId}/sub-categories")
    public ResponseEntity<ApiResponse<List<CategoryResponseDTO>>> getSubCategories(@PathVariable Long parentId) {
        ApiResponse<List<CategoryResponseDTO>> response = categoryService.getSubCategories(parentId);
        return ResponseEntity.ok(response);
    }

    // UPDATE - Mettre à jour une catégorie (Admin seulement)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> updateCategory(
            @PathVariable Long id,
            @Valid @ModelAttribute CategoryUpdateDTO request) {

        ApiResponse<CategoryResponseDTO> response = categoryService.updateCategory(id, request);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(response);
    }

    // DELETE - Supprimer une catégorie (Admin seulement)
    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> deleteCategory(@PathVariable Long id) {
        ApiResponse<String> response = categoryService.deleteCategory(id);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(response);
    }

    // SEARCH - Recherche avancée avec filtres
    @PostMapping("/search")
    public ResponseEntity<ApiResponse<Page<CategoryResponseDTO>>> searchCategories(
            @RequestBody CategorySearchDTO searchDTO) {

        ApiResponse<Page<CategoryResponseDTO>> response = categoryService.searchCategories(searchDTO);
        return ResponseEntity.ok(response);
    }

    // GET - Recherche simple par paramètres
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<CategoryResponseDTO>>> searchCategoriesByParams(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(required = false) Long parentId,
            @RequestParam(required = false) Boolean isMainCategory,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "displayOrder") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection) {

        CategorySearchDTO searchDTO = new CategorySearchDTO();
        searchDTO.setName(name);
        searchDTO.setIsActive(isActive);
        searchDTO.setParentId(parentId);
        searchDTO.setIsMainCategory(isMainCategory);
        searchDTO.setPage(page);
        searchDTO.setSize(size);
        searchDTO.setSortBy(sortBy);
        searchDTO.setSortDirection(sortDirection);

        ApiResponse<Page<CategoryResponseDTO>> response = categoryService.searchCategories(searchDTO);
        return ResponseEntity.ok(response);
    }

    // GET - Obtenir les statistiques des catégories (Admin seulement)
    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CategoryStatsDTO>> getCategoryStats() {
        ApiResponse<CategoryStatsDTO> response = categoryService.getCategoryStats();
        return ResponseEntity.ok(response);
    }

    // PATCH - Activer/Désactiver une catégorie (Admin seulement)
    @PatchMapping("/{id}/toggle-status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CategoryResponseDTO>> toggleCategoryStatus(@PathVariable Long id) {
        ApiResponse<CategoryResponseDTO> response = categoryService.toggleCategoryStatus(id);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(response);
    }

    // GET - Obtenir les catégories actives seulement (pour les dropdowns)
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<CategoryResponseDTO>>> getActiveCategories() {
        // Réutiliser la méthode de recherche avec filtre actif
        CategorySearchDTO searchDTO = new CategorySearchDTO();
        searchDTO.setIsActive(true);
        searchDTO.setSize(1000); // Récupérer toutes les catégories actives
        searchDTO.setSortBy("displayOrder");
        searchDTO.setSortDirection("ASC");

        ApiResponse<Page<CategoryResponseDTO>> response = categoryService.searchCategories(searchDTO);

        if (response.isSuccess()) {
            List<CategoryResponseDTO> categories = response.getData().getContent();
            return ResponseEntity.ok(ApiResponse.success(categories));
        } else {
            return ResponseEntity.badRequest().body(ApiResponse.error(response.getError()));
        }
    }

    // GET - Obtenir l'arbre hiérarchique complet des catégories
    @GetMapping("/tree")
    public ResponseEntity<ApiResponse<List<CategoryResponseDTO>>> getCategoryTree() {
        ApiResponse<List<CategoryResponseDTO>> response = categoryService.getMainCategories();
        return ResponseEntity.ok(response);
    }

    // GET - Obtenir les catégories par statut
    @GetMapping("/by-status")
    public ResponseEntity<ApiResponse<Page<CategoryResponseDTO>>> getCategoriesByStatus(
            @RequestParam boolean isActive,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        CategorySearchDTO searchDTO = new CategorySearchDTO();
        searchDTO.setIsActive(isActive);
        searchDTO.setPage(page);
        searchDTO.setSize(size);

        ApiResponse<Page<CategoryResponseDTO>> response = categoryService.searchCategories(searchDTO);
        return ResponseEntity.ok(response);
    }
}

package com.example.stage.stage.service;

import com.example.stage.stage.dto.*;
import com.example.stage.stage.entity.Category;
import com.example.stage.stage.repository.CategoryRepository;
import com.example.stage.stage.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final FileStorageService fileStorageService;

    // CREATE - Créer une nouvelle catégorie
    @Transactional
    public ApiResponse<CategoryResponseDTO> createCategory(CategoryCreateDTO request) {
        try {
            // Vérifier si le nom existe déjà
            if (categoryRepository.existsByNameIgnoreCase(request.getName())) {
                return ApiResponse.error("Une catégorie avec ce nom existe déjà");
            }



            // Vérifier si la catégorie parent existe (si spécifiée)
            Category parentCategory = null;
            if (request.getParentCategoryId() != null) {
                parentCategory = categoryRepository.findById(request.getParentCategoryId())
                        .orElse(null);
                if (parentCategory == null) {
                    return ApiResponse.error("Catégorie parent non trouvée");
                }
            }

            // Créer la catégorie
            Category category = new Category();
            category.setName(request.getName());
            category.setIcon(request.getIcon());
            category.setIsActive(request.getIsActive());
            category.setParentCategory(parentCategory);

            // Définir l'ordre d'affichage
            if (request.getDisplayOrder() != null && request.getDisplayOrder() > 0) {
                category.setDisplayOrder(request.getDisplayOrder());
            } else {
                // Auto-incrément de l'ordre d'affichage
                Integer nextOrder = parentCategory != null ?
                        categoryRepository.findNextDisplayOrderByParentId(parentCategory.getId()) :
                        categoryRepository.findNextDisplayOrderForMainCategories();
                category.setDisplayOrder(nextOrder);
            }

            // Gérer l'upload d'image
            if (request.getImage() != null && !request.getImage().isEmpty()) {
                String imageUrl = fileStorageService.storeFile(request.getImage(), "categories");
                category.setImage(imageUrl);
            }

            category = categoryRepository.save(category);

            return ApiResponse.success(new CategoryResponseDTO(category, true));

        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de la création: " + e.getMessage());
        }
    }

    // READ - Obtenir toutes les catégories avec pagination
    public ApiResponse<Page<CategoryResponseDTO>> getAllCategories(int page, int size, String sortBy, String sortDirection) {
        try {
            Sort sort = sortDirection.equalsIgnoreCase("ASC") ?
                    Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

            Pageable pageable = PageRequest.of(page, size, sort);
            Page<Category> categories = categoryRepository.findAll(pageable);

            Page<CategoryResponseDTO> response = categories.map(cat -> new CategoryResponseDTO(cat, false));

            return ApiResponse.success(response);
        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de la récupération: " + e.getMessage());
        }
    }

    // READ - Obtenir une catégorie par ID
    public ApiResponse<CategoryResponseDTO> getCategoryById(Long id) {
        try {
            Category category = categoryRepository.findById(id)
                    .orElse(null);

            if (category == null) {
                return ApiResponse.error("Catégorie non trouvée");
            }

            return ApiResponse.success(new CategoryResponseDTO(category, true));
        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de la récupération: " + e.getMessage());
        }
    }

    // READ - Obtenir les catégories principales
    public ApiResponse<List<CategoryResponseDTO>> getMainCategories() {
        try {
            List<Category> categories = categoryRepository.findByParentCategoryIsNullAndIsActiveTrueOrderByDisplayOrder();

            List<CategoryResponseDTO> response = categories.stream()
                    .map(cat -> new CategoryResponseDTO(cat, true))
                    .collect(Collectors.toList());

            return ApiResponse.success(response);
        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de la récupération: " + e.getMessage());
        }
    }

    // READ - Obtenir les sous-catégories d'une catégorie
    public ApiResponse<List<CategoryResponseDTO>> getSubCategories(Long parentId) {
        try {
            List<Category> categories = categoryRepository.findByParentCategoryIdAndIsActiveTrueOrderByDisplayOrder(parentId);

            List<CategoryResponseDTO> response = categories.stream()
                    .map(cat -> new CategoryResponseDTO(cat, false))
                    .collect(Collectors.toList());

            return ApiResponse.success(response);
        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de la récupération: " + e.getMessage());
        }
    }

    // UPDATE - Mettre à jour une catégorie
    @Transactional
    public ApiResponse<CategoryResponseDTO> updateCategory(Long id, CategoryUpdateDTO request) {
        try {
            Category category = categoryRepository.findById(id)
                    .orElse(null);

            if (category == null) {
                return ApiResponse.error("Catégorie non trouvée");
            }

            // Vérifier si le nom existe déjà pour une autre catégorie
            if (categoryRepository.existsByNameIgnoreCaseAndIdNot(request.getName(), id)) {
                return ApiResponse.error("Une catégorie avec ce nom existe déjà");
            }

            // Vérifier la catégorie parent
            Category parentCategory = null;
            if (request.getParentCategoryId() != null) {
                // Empêcher qu'une catégorie soit son propre parent ou parent de ses parents
                if (request.getParentCategoryId().equals(id)) {
                    return ApiResponse.error("Une catégorie ne peut pas être son propre parent");
                }

                parentCategory = categoryRepository.findById(request.getParentCategoryId())
                        .orElse(null);
                if (parentCategory == null) {
                    return ApiResponse.error("Catégorie parent non trouvée");
                }

                // Vérifier les références circulaires
                if (isCircularReference(id, request.getParentCategoryId())) {
                    return ApiResponse.error("Référence circulaire détectée");
                }
            }

            // Mettre à jour les champs
            category.setName(request.getName());
            category.setIcon(request.getIcon());
            category.setIsActive(request.getIsActive());
            category.setDisplayOrder(request.getDisplayOrder());
            category.setParentCategory(parentCategory);

            // Gérer l'image
            if (request.getRemoveImage() != null && request.getRemoveImage()) {
                // Supprimer l'image existante
                if (category.getImage() != null) {
                    fileStorageService.deleteFile(category.getImage());
                }
                category.setImage(null);
            }

            if (request.getImage() != null && !request.getImage().isEmpty()) {
                // Supprimer l'ancienne image
                if (category.getImage() != null) {
                    fileStorageService.deleteFile(category.getImage());
                }
                // Sauvegarder la nouvelle image
                String imageUrl = fileStorageService.storeFile(request.getImage(), "categories");
                category.setImage(imageUrl);
            }

            category = categoryRepository.save(category);

            return ApiResponse.success(new CategoryResponseDTO(category, true));

        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de la mise à jour: " + e.getMessage());
        }
    }


    public ApiResponse<List<CategoryHierarchyDTO>> getCategoryHierarchy() {
        try {
            // Récupérer toutes les catégories principales actives avec leurs sous-catégories
            List<Category> mainCategories = categoryRepository.findByParentCategoryIsNullAndIsActiveTrueOrderByDisplayOrder();

            List<CategoryHierarchyDTO> hierarchy = mainCategories.stream()
                    .map(CategoryHierarchyDTO::new)
                    .collect(Collectors.toList());

            return ApiResponse.success(hierarchy);
        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de la récupération de la hiérarchie: " + e.getMessage());
        }
    }

    public ApiResponse<List<CategoryMainOnlyDTO>> getMainCategoriesOnly() {
        try {
            List<Category> mainCategories = categoryRepository.findByParentCategoryIsNullAndIsActiveTrueOrderByDisplayOrder();

            List<CategoryMainOnlyDTO> result = mainCategories.stream()
                    .map(CategoryMainOnlyDTO::new)
                    .collect(Collectors.toList());

            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de la récupération: " + e.getMessage());
        }
    }

    /**
     * Obtenir la hiérarchie simplifiée : seulement ID et nom
     */
    public ApiResponse<List<CategorySimpleHierarchyDTO>> getSimpleCategoryHierarchy() {
        try {
            List<Category> mainCategories = categoryRepository.findByParentCategoryIsNullAndIsActiveTrueOrderByDisplayOrder();

            List<CategorySimpleHierarchyDTO> hierarchy = mainCategories.stream()
                    .map(CategorySimpleHierarchyDTO::new)
                    .collect(Collectors.toList());

            return ApiResponse.success(hierarchy);
        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de la récupération de la hiérarchie: " + e.getMessage());
        }
    }

    // DELETE - Supprimer une catégorie
    @Transactional
    public ApiResponse<String> deleteCategory(Long id) {
        try {
            Category category = categoryRepository.findById(id)
                    .orElse(null);

            if (category == null) {
                return ApiResponse.error("Catégorie non trouvée");
            }

            // Vérifier s'il y a des sous-catégories
            long subCategoryCount = categoryRepository.countByParentCategoryId(id);
            if (subCategoryCount > 0) {
                return ApiResponse.error("Impossible de supprimer une catégorie qui a des sous-catégories. Supprimez d'abord les sous-catégories.");
            }

            // Supprimer l'image associée
            if (category.getImage() != null) {
                fileStorageService.deleteFile(category.getImage());
            }

            categoryRepository.delete(category);

            return ApiResponse.success("Catégorie supprimée avec succès");

        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de la suppression: " + e.getMessage());
        }
    }

    // SEARCH - Recherche avancée
    public ApiResponse<Page<CategoryResponseDTO>> searchCategories(CategorySearchDTO searchDTO) {
        try {
            Sort sort = searchDTO.getSortDirection().equalsIgnoreCase("ASC") ?
                    Sort.by(searchDTO.getSortBy()).ascending() :
                    Sort.by(searchDTO.getSortBy()).descending();

            Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize(), sort);

            Page<Category> categories = categoryRepository.searchCategories(
                    searchDTO.getName(),
                    searchDTO.getIsActive(),
                    searchDTO.getParentId(),
                    searchDTO.getIsMainCategory(),
                    pageable
            );

            Page<CategoryResponseDTO> response = categories.map(cat -> new CategoryResponseDTO(cat, false));

            return ApiResponse.success(response);
        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de la recherche: " + e.getMessage());
        }
    }

    // Obtenir les statistiques des catégories
    public ApiResponse<CategoryStatsDTO> getCategoryStats() {
        try {
            long totalCategories = categoryRepository.count();
            long activeCategories = categoryRepository.countByIsActiveTrue();
            long mainCategories = categoryRepository.findByParentCategoryIsNullOrderByDisplayOrder().size();
            long subCategories = totalCategories - mainCategories;
            long inactiveCategories = totalCategories - activeCategories;

            CategoryStatsDTO stats = new CategoryStatsDTO();
            stats.setTotalCategories(totalCategories);
            stats.setActiveCategories(activeCategories);
            stats.setMainCategories(mainCategories);
            stats.setSubCategories(subCategories);
            stats.setInactiveCategories(inactiveCategories);

            return ApiResponse.success(stats);
        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de la récupération des statistiques: " + e.getMessage());
        }
    }

    // Activer/Désactiver une catégorie
    @Transactional
    public ApiResponse<CategoryResponseDTO> toggleCategoryStatus(Long id) {
        try {
            Category category = categoryRepository.findById(id)
                    .orElse(null);

            if (category == null) {
                return ApiResponse.error("Catégorie non trouvée");
            }

            category.setIsActive(!category.getIsActive());
            category = categoryRepository.save(category);

            return ApiResponse.success(new CategoryResponseDTO(category, false));
        } catch (Exception e) {
            return ApiResponse.error("Erreur lors du changement de statut: " + e.getMessage());
        }
    }

    // Méthode utilitaire pour détecter les références circulaires
    private boolean isCircularReference(Long categoryId, Long parentId) {
        Category parent = categoryRepository.findById(parentId).orElse(null);

        while (parent != null) {
            if (parent.getId().equals(categoryId)) {
                return true;
            }
            parent = parent.getParentCategory();
        }

        return false;
    }
}
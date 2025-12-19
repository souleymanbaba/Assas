package com.example.stage.stage.dto;

import com.example.stage.stage.entity.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class CategoryResponseDTO {
    private Long id;
    private String name;
    private String icon;
    private String imageUrl;
    private Boolean isActive;
    private Integer displayOrder;
    private Long parentCategoryId;
    private String parentCategoryName;
    private boolean isMainCategory;
    private boolean isSubCategory;
    private List<CategoryResponseDTO> subCategories;
    private int subCategoryCount;

    public CategoryResponseDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.icon = category.getIcon();
        this.imageUrl = category.getImage();
        this.isActive = category.getIsActive();
        this.displayOrder = category.getDisplayOrder();
        this.isMainCategory = category.isMainCategory();
        this.isSubCategory = category.isSubCategory();

        if (category.getParentCategory() != null) {
            this.parentCategoryId = category.getParentCategory().getId();
            this.parentCategoryName = category.getParentCategory().getName();
        }

        if (category.getSubCategories() != null) {
            this.subCategories = category.getSubCategories().stream()
                    .map(CategoryResponseDTO::new)
                    .collect(Collectors.toList());
            this.subCategoryCount = category.getSubCategories().size();
        } else {
            this.subCategoryCount = 0;
        }
    }

    // Constructor sans sous-catégories (pour éviter la récursion infinie)
    public CategoryResponseDTO(Category category, boolean includeSubCategories) {
        this.id = category.getId();
        this.name = category.getName();
        this.icon = category.getIcon();
        this.imageUrl = category.getImage();
        this.isActive = category.getIsActive();
        this.displayOrder = category.getDisplayOrder();
        this.isMainCategory = category.isMainCategory();
        this.isSubCategory = category.isSubCategory();

        if (category.getParentCategory() != null) {
            this.parentCategoryId = category.getParentCategory().getId();
            this.parentCategoryName = category.getParentCategory().getName();
        }

        if (includeSubCategories && category.getSubCategories() != null) {
            this.subCategories = category.getSubCategories().stream()
                    .map(sub -> new CategoryResponseDTO(sub, false))
                    .collect(Collectors.toList());
            this.subCategoryCount = category.getSubCategories().size();
        } else if (category.getSubCategories() != null) {
            this.subCategoryCount = category.getSubCategories().size();
        } else {
            this.subCategoryCount = 0;
        }
    }
}

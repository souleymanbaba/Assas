package com.example.stage.stage.dto;

import com.example.stage.stage.entity.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class CategoryHierarchyDTO {
    private Long id;
    private String name;
    private String icon;
    private String imageUrl;
    private List<SubCategoryDTO> subCategories;

    public CategoryHierarchyDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.icon = category.getIcon();
        this.imageUrl = category.getImage();

        if (category.getSubCategories() != null) {
            this.subCategories = category.getSubCategories().stream()
                    .filter(subCat -> subCat.getIsActive()) // Seulement les sous-catégories actives
                    .map(SubCategoryDTO::new)
                    .collect(Collectors.toList());
        }
    }

    @Data
    @NoArgsConstructor
    public static class SubCategoryDTO {
        private Long id;
        private String name;

        public SubCategoryDTO(Category category) {
            this.id = category.getId();
            this.name = category.getName();
        }
    }
}


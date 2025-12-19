package com.example.stage.stage.dto;

import com.example.stage.stage.entity.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class CategorySimpleHierarchyDTO {
    private Long id;
    private String name;
    private List<SimpleSubCategoryDTO> subCategories;

    public CategorySimpleHierarchyDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();

        if (category.getSubCategories() != null) {
            this.subCategories = category.getSubCategories().stream()
                    .filter(subCat -> subCat.getIsActive()) // Seulement les actives
                    .map(SimpleSubCategoryDTO::new)
                    .collect(Collectors.toList());
        }
    }

    @Data
    @NoArgsConstructor
    public static class SimpleSubCategoryDTO {
        private Long id;
        private String name;

        public SimpleSubCategoryDTO(Category category) {
            this.id = category.getId();
            this.name = category.getName();
        }
    }
}

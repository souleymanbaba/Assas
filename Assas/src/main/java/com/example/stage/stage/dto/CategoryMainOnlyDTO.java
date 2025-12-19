package com.example.stage.stage.dto;

import com.example.stage.stage.entity.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryMainOnlyDTO {
    private Long id;
    private String name;
    private String icon;
    private String imageUrl;
    private int subCategoryCount;

    public CategoryMainOnlyDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.icon = category.getIcon();
        this.imageUrl = category.getImage();
        this.subCategoryCount = category.getSubCategories() != null ?
                (int) category.getSubCategories().stream().filter(sub -> sub.getIsActive()).count() : 0;
    }
}

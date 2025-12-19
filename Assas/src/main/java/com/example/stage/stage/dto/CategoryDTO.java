package com.example.stage.stage.dto;

import com.example.stage.stage.entity.Professional;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private Long id;
    private String name;
    private String icon;
    private String image;
    private Boolean isActive;
    private Integer displayOrder;

    // Pour les relations parent/enfant
    private Long parentCategoryId;
    private String parentCategoryName;
    private List<CategoryDTO> subCategories;

    // Nombre de professionnels dans cette catégorie
    private Long professionalCount;
}
package com.example.stage.stage.dto;

import com.example.stage.stage.entity.Professional;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategorySimpleDTO {
    private Long id;
    private String name;
    private String icon;
    private String image;
    private Boolean isActive;
    private Integer displayOrder;
    private List<String> subCategoryNames;
    private Integer subCategoryCount;
}
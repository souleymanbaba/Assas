package com.example.stage.stage.dto;

import lombok.Data;

@Data
public class CategorySearchDTO {
    private String name;
    private Boolean isActive;
    private Long parentId;
    private Boolean isMainCategory; // true = principales, false = sous-catégories, null = toutes
    private int page = 0;
    private int size = 10;
    private String sortBy = "displayOrder";
    private String sortDirection = "ASC";
}

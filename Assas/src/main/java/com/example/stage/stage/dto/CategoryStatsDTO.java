package com.example.stage.stage.dto;

import lombok.Data;

@Data
public class CategoryStatsDTO {
    private long totalCategories;
    private long activeCategories;
    private long mainCategories;
    private long subCategories;
    private long inactiveCategories;
}
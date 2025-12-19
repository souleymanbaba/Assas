package com.example.stage.stage.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComparisonCriteriaDTO {
    private String key;
    private String name;
    private String description;
    private String type; // RATING, DISTANCE, EXPERIENCE, PRICE, BOOLEAN
    private String unit;
    private Boolean isDefault;
    private Integer displayOrder;
}


package com.example.stage.stage.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComparisonCriteriaResultDTO {
    private String criteriaName;
    private String criteriaType; // RATING, NUMERIC, BOOLEAN, TEXT
    private String unit; // km, €, années, etc.
    private Object bestValue;
    private Object worstValue;
    private List<CriteriaValueDTO> values; // Valeur pour chaque professionnel
    private String recommendation;
}


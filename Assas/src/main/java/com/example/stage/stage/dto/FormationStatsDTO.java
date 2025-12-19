package com.example.stage.stage.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormationStatsDTO {
    private Long totalFormations;
    private Long formationsWithImageFrancais;
    private Long formationsWithImageArabe;
    private Long formationsWithBothImages;
    private Long formationsWithoutImages;
}
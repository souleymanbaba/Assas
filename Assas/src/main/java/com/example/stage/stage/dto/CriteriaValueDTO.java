package com.example.stage.stage.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CriteriaValueDTO {
    private Long professionalId;
    private String professionalName;
    private Object value;
    private String displayValue;
    private Boolean isBest;
    private Boolean isWorst;
}


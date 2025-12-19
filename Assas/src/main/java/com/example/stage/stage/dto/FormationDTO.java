package com.example.stage.stage.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormationDTO {
    private Long id;
    private String title;
    private String category;
    private String description;
    private Boolean isVisible;
    private Integer displayOrder;
    private LocalDateTime createdAt;
    private List<String> imageUrls;
}


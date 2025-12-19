package com.example.stage.stage.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO pour la recherche avancée d'avis
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewSearchDTO {
    private Long professionalId;
    private String clientEmail;
    private Integer rating;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String comment;
    private int page = 0;
    private int size = 10;
    private String sortBy = "createdAt";
    private String sortDirection = "DESC";
}
package com.example.stage.stage.dto;

import com.example.stage.stage.entity.Formation;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO de réponse pour une formation
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormationResponseDTO {
    private Long id;
    private String imageFrancais;
    private String imageArabe;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime endDate;


    public FormationResponseDTO(Formation formation) {
        this.id = formation.getId();
        this.imageFrancais = formation.getImageFrancais();
        this.imageArabe = formation.getImageArabe();
        this.createdAt = formation.getCreatedAt();
        this.updatedAt = formation.getUpdatedAt();
        this.endDate = formation.getEndDate(); // <--- à ajouter
    }

}
package com.example.stage.stage.dto;

import com.example.stage.stage.entity.Publication;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PublicationResponseDTO {
    private Long id;
    private String title;
    private String imageUrl;
    private String email;
    private Integer place;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isActive; // Calculé selon les dates
    private boolean isExpired; // Calculé selon les dates
    private boolean isUpcoming; // Calculé selon les dates

    public PublicationResponseDTO(Publication publication) {
        this.id = publication.getId();
        this.title = publication.getTitle();
        this.imageUrl = publication.getImageUrl();
        this.email = publication.getEmail();
        this.place = publication.getPlace();
        this.startDate = publication.getStartDate();
        this.endDate = publication.getEndDate();
        this.createdAt = publication.getCreatedAt();
        this.updatedAt = publication.getUpdatedAt();

        // Calculer les statuts basés sur les dates
        LocalDateTime now = LocalDateTime.now();
        this.isActive = now.isAfter(startDate) && now.isBefore(endDate);
        this.isExpired = now.isAfter(endDate);
        this.isUpcoming = now.isBefore(startDate);
    }
}

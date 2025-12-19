package com.example.stage.stage.dto;

import com.example.stage.stage.entity.Review;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO de réponse pour un avis
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDTO {
    private Long id;
    private Long professionalId;
    private String professionalName;
    private String clientEmail;
    private String clientName;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ReviewResponseDTO(Review review) {
        this.id = review.getId();
        this.professionalId = review.getProfessional().getId();
        this.professionalName = review.getProfessional().getFirstName() + " " + review.getProfessional().getLastName();
        this.clientEmail = review.getClientEmail();
        this.clientName = review.getClientName();
        this.rating = review.getRating();
        this.comment = review.getComment();
        this.createdAt = review.getCreatedAt();
        this.updatedAt = review.getUpdatedAt();
    }
}

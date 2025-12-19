package com.example.stage.stage.dto;

import com.example.stage.stage.entity.Professional;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessionalDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String whatsapp;
    private String profilePhoto;
    private String description;
    private String address;
    private Double latitude;
    private Double longitude;
    private String matricule;
    private String companyName;
    private Professional.CertificationStatus certificationStatus;
    private LocalDateTime validatedAt;
    private Double ratingAverage;
    private Integer ratingCount;
    private Boolean isVisible;
    private Boolean isSuspended;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CategoryDTO> specialties;
    private List<ReviewDTO> reviews;

}

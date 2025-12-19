package com.example.stage.stage.dto;

import com.example.stage.stage.entity.Publication;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PublicationDTO {
    private Long id;
    private String professionalName;
    private String professionalMatricule;
    private String title;
    private String content;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean isActive;
    private LocalDateTime moderatedAt;
    private LocalDateTime createdAt;
    private List<String> imageUrls;
}

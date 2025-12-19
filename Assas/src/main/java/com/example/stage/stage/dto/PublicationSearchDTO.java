package com.example.stage.stage.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PublicationSearchDTO {
    private String title;
    private String email;
    private Integer place;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status; // "active", "expired", "upcoming", "all"
    private int page = 0;
    private int size = 10;
    private String sortBy = "createdAt";
    private String sortDirection = "DESC";
}

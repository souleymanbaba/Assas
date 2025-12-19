package com.example.stage.stage.dto;

import lombok.Data;

@Data
public class PublicationStatsDTO {
    private long totalPublications;
    private long activePublications;
    private long expiredPublications;
    private long upcomingPublications;
    private long publicationsByEmail;
}
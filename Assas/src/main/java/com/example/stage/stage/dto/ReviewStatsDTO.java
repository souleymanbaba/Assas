package com.example.stage.stage.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ReviewStatsDTO {
    private long totalReviews;
    private Double averageRating;
    private long reviewsCount1Star;
    private long reviewsCount2Star;
    private long reviewsCount3Star;
    private long reviewsCount4Star;
    private long reviewsCount5Star;
    private Map<Integer, Long> ratingDistribution;
}

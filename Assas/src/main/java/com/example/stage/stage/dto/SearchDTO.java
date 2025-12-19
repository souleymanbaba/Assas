package com.example.stage.stage.dto;


import com.example.stage.stage.entity.Professional;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchDTO {
    private String keyword;
    private List<Long> categoryIds;
    private Double latitude;
    private Double longitude;
    private Double radiusKm;
    private Double minRating;
    private Boolean certifiedOnly;
    private String sortBy = "rating"; // rating, proximity, recent
    private Integer page = 0;
    private Integer size = 20;
}



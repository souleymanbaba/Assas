package com.example.stage.stage.dto;

import lombok.Data;

@Data
public class ProfessionalSearchDTO {
    private String firstName;
    private String lastName;
    private String serviceType;
    private Long specialtyId;
    private String certificationStatus;
    private int page = 0;
    private int size = 10;
    private String sortBy = "createdAt";
    private String sortDirection = "DESC";
}

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
public class CertificationRequestDTO {
    private Long professionalId;
    private String professionalName;
    private String matricule;
    private String companyName;
    private Professional.CertificationStatus status;
    private LocalDateTime createdAt;
    private List<DocumentDTO> documents;
    private int documentCount;
    private String email;
    private String phone;
}
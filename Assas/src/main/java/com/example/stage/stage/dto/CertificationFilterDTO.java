package com.example.stage.stage.dto;
import com.example.stage.stage.entity.Professional;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificationFilterDTO {
    private Professional.CertificationStatus status;
    private LocalDateTime submittedAfter;
    private LocalDateTime submittedBefore;
    private String searchTerm; // recherche dans nom, email, matricule
    private Boolean hasDocuments;
    private String city;
}

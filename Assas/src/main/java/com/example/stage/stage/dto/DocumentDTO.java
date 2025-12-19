package com.example.stage.stage.dto;

import com.example.stage.stage.entity.CertificationDocument;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDTO {
    private Long id;
    private String documentName;
    private String documentUrl;
    private CertificationDocument.DocumentType documentType;
    private LocalDateTime uploadedAt;
}

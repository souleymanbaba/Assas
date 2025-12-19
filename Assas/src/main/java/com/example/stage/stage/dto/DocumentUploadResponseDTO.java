package com.example.stage.stage.dto;

import com.example.stage.stage.entity.CertificationDocument;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentUploadResponseDTO {
    private String documentUrl;
    private String documentName;
    private CertificationDocument.DocumentType documentType;
    private Long fileSize;
    private String message;
}


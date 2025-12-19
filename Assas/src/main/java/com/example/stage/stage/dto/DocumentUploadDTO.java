package com.example.stage.stage.dto;

import com.example.stage.stage.entity.CertificationDocument;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentUploadDTO {

    @NotBlank(message = "Le nom du document est obligatoire")
    private String documentName;

    @NotBlank(message = "L'URL du document est obligatoire")
    private String documentUrl;

    @NotNull(message = "Le type de document est obligatoire")
    private CertificationDocument.DocumentType documentType;
}

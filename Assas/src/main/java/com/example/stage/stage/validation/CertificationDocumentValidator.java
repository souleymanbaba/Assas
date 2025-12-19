package com.example.stage.stage.validation;

import com.example.stage.stage.dto.DocumentUploadDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CertificationDocumentValidator implements ConstraintValidator<ValidCertificationDocument, DocumentUploadDTO> {

    @Override
    public boolean isValid(DocumentUploadDTO document, ConstraintValidatorContext context) {
        if (document == null) {
            return false;
        }

        // Vérifier l'URL du document
        if (document.getDocumentUrl() == null || document.getDocumentUrl().trim().isEmpty()) {
            addViolation(context, "L'URL du document est obligatoire");
            return false;
        }

        // Vérifier que l'URL est valide
        if (!isValidUrl(document.getDocumentUrl())) {
            addViolation(context, "L'URL du document n'est pas valide");
            return false;
        }

        // Vérifier le type de document
        if (document.getDocumentType() == null) {
            addViolation(context, "Le type de document est obligatoire");
            return false;
        }

        return true;
    }

    private boolean isValidUrl(String url) {
        return url.startsWith("http://") || url.startsWith("https://");
    }

    private void addViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}

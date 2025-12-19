package com.example.stage.stage.validation;

import com.example.stage.stage.dto.CertificationDocumentValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCertificationDocument {
    String message() default "Document de certification invalide";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

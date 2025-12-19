// =====================================
// 7. CONTROLLER - CertificationController.java
// =====================================
package com.example.stage.stage.controller;

import com.example.stage.stage.dto.*;
import com.example.stage.stage.entity.CertificationRequest;
import com.example.stage.stage.response.ApiResponse;
import com.example.stage.stage.service.CertificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/certifications")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CertificationController {

    private final CertificationService certificationService;

    // API 1: Soumettre une demande de certification
    @PostMapping("/submit")
    @Operation(
            summary = "Soumettre une demande de certification",
            description = "Permet à un professionnel de soumettre sa demande de certification avec les documents requis. " +
                    "L'identification se fait automatiquement via le token JWT."
    )
    @PreAuthorize("hasRole('PROFESSIONAL')")
    public ResponseEntity<ApiResponse<String>> submitCertificationRequest(
            @Parameter(description = "Token JWT du professionnel", required = true)
            @RequestHeader("Authorization") String authHeader,
            @Valid @ModelAttribute CertificationSubmissionDTO request) {

        try {
            // Extraire le token du header Authorization
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.error("Token d'autorisation manquant ou invalide"));
            }

            String token = authHeader.substring(7); // Enlever "Bearer "


            ApiResponse<String> response = certificationService.submitCertificationRequest(token, request);

            if (response.isSuccess()) {
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }


    // API 2: Accepter une demande de certification (Admin seulement)
    @PatchMapping("/{requestId}/accept")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CertificationResponseDTO>> acceptCertification(
            @PathVariable Long requestId,
            @Valid @RequestBody CertificationDecisionDTO decision,
            Authentication authentication) {

        // Récupérer l'ID de l'admin depuis le token (à adapter selon votre implémentation)
        Long adminId = getCurrentUserId(authentication);

        ApiResponse<CertificationResponseDTO> response =
                certificationService.acceptCertification(requestId, decision, adminId);

        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(response);
    }

    // API 3: Rejeter une demande de certification (Admin seulement)
    @PatchMapping("/{requestId}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CertificationResponseDTO>> rejectCertification(
            @PathVariable Long requestId,
            @Valid @RequestBody CertificationDecisionDTO decision,
            Authentication authentication) {

        Long adminId = getCurrentUserId(authentication);

        ApiResponse<CertificationResponseDTO> response =
                certificationService.rejectCertification(requestId, decision, adminId);

        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(response);
    }

    // API 4: Révoquer une certification (Admin seulement)
    @PatchMapping("/{requestId}/revoke")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<CertificationResponseDTO>> revokeCertification(
            @PathVariable Long requestId,
            @Valid @RequestBody CertificationRevocationDTO revocation,
            Authentication authentication) {

        Long adminId = getCurrentUserId(authentication);

        ApiResponse<CertificationResponseDTO> response =
                certificationService.revokeCertification(requestId, revocation, adminId);

        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(response);
    }

    // Obtenir toutes les demandes de certification (Admin seulement)
    @GetMapping
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<CertificationResponseDTO>>> getAllCertificationRequests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "submittedAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {

        ApiResponse<Page<CertificationResponseDTO>> response =
                certificationService.getAllCertificationRequests(page, size, sortBy, sortDirection);

        return ResponseEntity.ok(response);
    }

    // Obtenir les demandes par statut (Admin seulement)
    @GetMapping("/status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<CertificationResponseDTO>>> getCertificationRequestsByStatus(
            @PathVariable String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        try {
            CertificationRequest.CertificationStatus certificationStatus =
                    CertificationRequest.CertificationStatus.valueOf(status.toUpperCase());

            ApiResponse<Page<CertificationResponseDTO>> response =
                    certificationService.getCertificationRequestsByStatus(certificationStatus, page, size);

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Statut de certification invalide"));
        }
    }

    // Obtenir la demande de certification d'un professionnel
    @GetMapping("/professional/{professionalId}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('PROFESSIONAL') and @professionalService.isProfessionalOwner(#professionalId, authentication.name))")
    public ResponseEntity<ApiResponse<CertificationResponseDTO>> getCertificationRequestByProfessionalId(
            @PathVariable Long professionalId) {

        ApiResponse<CertificationResponseDTO> response =
                certificationService.getCertificationRequestByProfessionalId(professionalId);

        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(response);
    }

    // Obtenir ma demande de certification (Professionnel connecté)
    @GetMapping("/my-request")
    @PreAuthorize("hasRole('PROFESSIONAL')")
    public ResponseEntity<ApiResponse<CertificationResponseDTO>> getMyCertificationRequest(
            Authentication authentication) {

        // Récupérer l'ID du professionnel connecté
        String email = authentication.getName();
        // Vous devez implémenter une méthode pour récupérer l'ID professionnel par email
        Long professionalId = getProfessionalIdByEmail(email);

        if (professionalId == null) {
            return ResponseEntity.notFound().build();
        }

        ApiResponse<CertificationResponseDTO> response =
                certificationService.getCertificationRequestByProfessionalId(professionalId);

        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(response);
    }

    // Méthode utilitaire pour récupérer l'ID de l'utilisateur connecté
    private Long getCurrentUserId(Authentication authentication) {
        // À implémenter selon votre système d'authentification
        // Exemple : extraire l'ID depuis le JWT token
        return 1L; // Placeholder
    }

    // Méthode utilitaire pour récupérer l'ID du professionnel par email
    private Long getProfessionalIdByEmail(String email) {
        // À implémenter selon votre service
        // Exemple : utiliser professionalService.getProfessionalByEmail(email).getData().getId()
        return 1L; // Placeholder
    }
}

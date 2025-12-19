package com.example.stage.stage.controller;

import com.example.stage.stage.dto.*;
import com.example.stage.stage.response.ApiResponse;
import com.example.stage.stage.service.ProfessionalService;
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

import java.util.List;

@RestController
@RequestMapping("/api/professionals")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProfessionalController {

    private final ProfessionalService professionalService;


    @GetMapping("/complete-profile")
    @Operation(
            summary = "Récupérer le profil complet du professionnel via Header",
            description = "Retourne tous les détails d'un professionnel authentifié via son token dans le header Authorization"
    )
    public ResponseEntity<ApiResponse<ProfessionalDetailDTO>> getCompleteProfile(
            @Parameter(description = "Token JWT du professionnel", required = true)
            @RequestHeader("Authorization") String authHeader) {

        try {
            // Extraire le token du header Authorization
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.error("Token d'autorisation manquant ou invalide"));
            }

            String token = authHeader.substring(7); // Enlever "Bearer "


            ApiResponse<ProfessionalDetailDTO> response = professionalService.getProfessionalProfile(token);

            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }


    /**
     * API UNIFIÉE: Récupérer les professionnels avec filtrage par catégorie et/ou serviceType
     */
    @GetMapping("/filtered")
    @Operation(
            summary = "Récupérer les professionnels avec filtres",
            description = "API unifiée pour récupérer les professionnels avec filtrage optionnel par catégorie et/ou serviceType. " +
                    "Les professionnels certifiés apparaissent en premier, triés par rating décroissant."
    )

    public ResponseEntity<ApiResponse<List<ProfessionalSummaryDTO>>> getProfessionalsFiltered(
            @Parameter(description = "ID de la catégorie (optionnel)", required = false)
            @RequestParam(required = false) Long categoryId,
            @Parameter(description = "Type de service (optionnel)", required = false)
            @RequestParam(required = false) String serviceType) {

        try {
            ApiResponse<List<ProfessionalSummaryDTO>> response =
                    professionalService.getProfessionalsFiltered(categoryId, serviceType);

            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }

    /**
     * Méthode 1 Alternative: Récupérer le profil complet via token en paramètre
     */
    @GetMapping("/complete-profile/token")
    @Operation(
            summary = "Récupérer le profil complet par token en paramètre",
            description = "Alternative pour récupérer le profil complet en passant le token en paramètre de requête"
    )
    public ResponseEntity<ApiResponse<ProfessionalDetailDTO>> getCompleteProfileByToken(
            @Parameter(description = "Token JWT du professionnel", required = true)
            @RequestParam String token) {

        try {

            ApiResponse<ProfessionalDetailDTO> response = professionalService.getProfessionalProfile(token);

            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }

    @GetMapping("/by-category/{categoryId}")
    @Operation(
            summary = "Récupérer les professionnels par catégorie",
            description = "Retourne tous les professionnels d'une catégorie spécifique, ordonnés par certification puis rating"
    )
    public ResponseEntity<ApiResponse<List<ProfessionalSummaryDTO>>> getProfessionalsByCategory(
            @Parameter(description = "ID de la catégorie", required = true)
            @PathVariable Long categoryId) {

        try {

            ApiResponse<List<ProfessionalSummaryDTO>> response =
                    professionalService.getProfessionalsByCategory(categoryId);

            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }


    /**
     * Méthode 2: Récupérer tous les professionnels ordonnés par certification et rating
     */
    @GetMapping("/list-ordered")
    @Operation(
            summary = "Récupérer tous les professionnels ordonnés",
            description = "Retourne tous les professionnels ordonnés par statut de certification et rating. " +
                    "Les professionnels certifiés apparaissent en premier, triés par rating décroissant, " +
                    "suivis des professionnels non certifiés également triés par rating. " +
                    "Filtrage optionnel par serviceType."
    )
    public ResponseEntity<ApiResponse<List<ProfessionalSummaryDTO>>> getAllProfessionalsOrdered(
            @Parameter(description = "Type de service pour filtrer les professionnels (optionnel)", required = false)
            @RequestParam(required = false) String serviceType) {

        try {


            ApiResponse<List<ProfessionalSummaryDTO>> response =
                    professionalService.getAllProfessionalsOrderedByServiceType(serviceType);

            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }

    // CREATE - Inscription d'un nouveau professionnel (public)
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> registerProfessional(@Valid @RequestBody ProfessionalCreateDTO request) {
        ApiResponse<String> response = professionalService.registerProfessional(request);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST)
                .body(response);
    }

    // READ - Obtenir tous les professionnels avec pagination
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSIONAL')")
    public ResponseEntity<ApiResponse<Page<ProfessionalResponseDTO>>> getAllProfessionals(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {

        ApiResponse<Page<ProfessionalResponseDTO>> response =
                professionalService.getAllProfessionals(page, size, sortBy, sortDirection);

        return ResponseEntity.ok(response);
    }

    // READ - Obtenir un professionnel par ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('PROFESSIONAL') and @professionalService.isProfessionalOwner(#id, authentication.name))")
    public ResponseEntity<ApiResponse<ProfessionalResponseDTO>> getProfessionalById(@PathVariable Long id) {
        ApiResponse<ProfessionalResponseDTO> response = professionalService.getProfessionalById(id);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(response);
    }

    // READ - Obtenir un professionnel par email
    @GetMapping("/email/{email}")
    @PreAuthorize("hasRole('ADMIN') or authentication.name == #email")
    public ResponseEntity<ApiResponse<ProfessionalResponseDTO>> getProfessionalByEmail(@PathVariable String email) {
        ApiResponse<ProfessionalResponseDTO> response = professionalService.getProfessionalByEmail(email);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(response);
    }

    // UPDATE - Mettre à jour un professionnel
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('PROFESSIONAL') and @professionalService.isProfessionalOwner(#id, authentication.name))")
    public ResponseEntity<ApiResponse<ProfessionalResponseDTO>> updateProfessional(
            @PathVariable Long id,
            @Valid @RequestBody ProfessionalUpdateDTO request) {

        ApiResponse<ProfessionalResponseDTO> response = professionalService.updateProfessional(id, request);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(response);
    }

    // DELETE - Supprimer un professionnel (Admin seulement)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> deleteProfessional(@PathVariable Long id) {
        ApiResponse<String> response = professionalService.deleteProfessional(id);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(response);
    }

    // SEARCH - Recherche avancée avec filtres
    @PostMapping("/search")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSIONAL')")
    public ResponseEntity<ApiResponse<Page<ProfessionalResponseDTO>>> searchProfessionals(
            @RequestBody ProfessionalSearchDTO searchDTO) {

        ApiResponse<Page<ProfessionalResponseDTO>> response = professionalService.searchProfessionals(searchDTO);
        return ResponseEntity.ok(response);
    }

    // GET - Recherche simple par paramètres
    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSIONAL')")
    public ResponseEntity<ApiResponse<Page<ProfessionalResponseDTO>>> searchProfessionalsByParams(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String serviceType,
            @RequestParam(required = false) Long specialtyId,
            @RequestParam(required = false) String certificationStatus,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {

        ProfessionalSearchDTO searchDTO = new ProfessionalSearchDTO();
        searchDTO.setFirstName(firstName);
        searchDTO.setLastName(lastName);
        searchDTO.setServiceType(serviceType);
        searchDTO.setSpecialtyId(specialtyId);
        searchDTO.setCertificationStatus(certificationStatus);
        searchDTO.setPage(page);
        searchDTO.setSize(size);
        searchDTO.setSortBy(sortBy);
        searchDTO.setSortDirection(sortDirection);

        ApiResponse<Page<ProfessionalResponseDTO>> response = professionalService.searchProfessionals(searchDTO);
        return ResponseEntity.ok(response);
    }

    // UPDATE - Mettre à jour le statut de certification (Admin seulement)
    @PatchMapping("/{id}/certification-status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ProfessionalResponseDTO>> updateCertificationStatus(
            @PathVariable Long id,
            @Valid @RequestBody CertificationStatusUpdateDTO request) {

        ApiResponse<ProfessionalResponseDTO> response =
                professionalService.updateCertificationStatus(id, request);

        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(response);
    }

    // GET - Obtenir les statistiques des professionnels (Admin seulement)
    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ProfessionalStatsDTO>> getProfessionalStats() {
        ApiResponse<ProfessionalStatsDTO> response = professionalService.getProfessionalStats();
        return ResponseEntity.ok(response);
    }

    // GET - Obtenir les professionnels par statut de certification
    @GetMapping("/by-status/{status}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<ProfessionalResponseDTO>>> getProfessionalsByStatus(
            @PathVariable String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        ProfessionalSearchDTO searchDTO = new ProfessionalSearchDTO();
        searchDTO.setCertificationStatus(status);
        searchDTO.setPage(page);
        searchDTO.setSize(size);

        ApiResponse<Page<ProfessionalResponseDTO>> response = professionalService.searchProfessionals(searchDTO);
        return ResponseEntity.ok(response);
    }

    // GET - Obtenir le profil du professionnel connecté
    @GetMapping("/profile")
    @PreAuthorize("hasRole('PROFESSIONAL')")
    public ResponseEntity<ApiResponse<ProfessionalResponseDTO>> getMyProfile(Authentication authentication) {
        String email = authentication.getName();
        ApiResponse<ProfessionalResponseDTO> response = professionalService.getProfessionalByEmail(email);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(response);
    }

    // PUT - Mettre à jour le profil du professionnel connecté
    @PutMapping("/profile")
    @PreAuthorize("hasRole('PROFESSIONAL')")
    public ResponseEntity<ApiResponse<ProfessionalResponseDTO>> updateMyProfile(
            Authentication authentication,
            @Valid @RequestBody ProfessionalUpdateDTO request) {

        String email = authentication.getName();

        // Trouver l'ID du professionnel connecté
        ApiResponse<ProfessionalResponseDTO> currentProfessional = professionalService.getProfessionalByEmail(email);
        if (!currentProfessional.isSuccess()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(currentProfessional);
        }

        Long professionalId = currentProfessional.getData().getId();
        ApiResponse<ProfessionalResponseDTO> response = professionalService.updateProfessional(professionalId, request);

        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(response);
    }

    @GetMapping("/{id}/with-images")
    public ResponseEntity<ApiResponse<ProfessionalResponseDTO>> getProfessionalWithImages(@PathVariable Long id) {
        ApiResponse<ProfessionalResponseDTO> response = professionalService.getProfessionalWithImages(id);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(response);
    }

    // GET - Obtenir un professionnel avec ses images par User ID
    @GetMapping("/user/{userId}/with-images")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ProfessionalResponseDTO>> getProfessionalByUserIdWithImages(@PathVariable Long userId) {
        ApiResponse<ProfessionalResponseDTO> response = professionalService.getProfessionalByUserIdWithImages(userId);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(response);
    }
}
package com.example.stage.stage.controller;

import com.example.stage.stage.dto.*;
import com.example.stage.stage.response.ApiResponse;
import com.example.stage.stage.service.FormationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/formations")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
@Tag(name = "Formations", description = "API de gestion des formations (images français/arabe)")
public class FormationController {

    private final FormationService formationService;

    // === CRUD DE BASE (URLs) ===

    /**
     * Créer une nouvelle formation avec fichiers
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Créer une formation avec fichiers",
            description = "Crée une nouvelle formation en uploadant des fichiers images"
    )
    public ResponseEntity<ApiResponse<FormationResponseDTO>> createFormationWithFiles(
            @ModelAttribute FormationCreateFileDTO request) {

        try {
            log.info("Création d'une nouvelle formation avec fichiers");
            ApiResponse<FormationResponseDTO> response = formationService.createFormationWithFiles(request);
            if (response.isSuccess()) {
                log.info("Formation créée avec succès");
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                log.warn("Échec de la création de formation: {}", response.getError());
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            log.error("Erreur lors de la création de formation: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }

    @GetMapping("/non-expirees")
    @Operation(summary = "Récupérer les formations dont la date de fin n'est pas atteinte")
    public ResponseEntity<ApiResponse<List<FormationResponseDTO>>> getFormationsNonExpirees() {
        return ResponseEntity.ok(formationService.getFormationsNonExpirees());
    }


    /**
     * Créer une nouvelle formation (compatibilité)
     */
    @PostMapping
    @Operation(
            summary = "Créer une formation",
            description = "Crée une nouvelle formation avec des URLs d'images (compatibilité)"
    )
    public ResponseEntity<ApiResponse<FormationResponseDTO>> createFormation(
            @Valid @RequestBody FormationCreateDTO request) {

        try {
            log.info("Création d'une nouvelle formation");
            ApiResponse<FormationResponseDTO> response = formationService.createFormation(request);
            if (response.isSuccess()) {
                log.info("Formation créée avec succès");
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                log.warn("Échec de la création de formation: {}", response.getError());
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            log.error("Erreur lors de la création de formation: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }

    /**
     * Créer une nouvelle formation avec URLs
     */
    @PostMapping("/url")
    @Operation(
            summary = "Créer une formation avec URLs",
            description = "Crée une nouvelle formation avec des URLs d'images"
    )
    public ResponseEntity<ApiResponse<FormationResponseDTO>> createFormationWithUrl(
            @Valid @RequestBody FormationCreateDTO request) {

        try {
            log.info("Création d'une nouvelle formation avec URLs");
            ApiResponse<FormationResponseDTO> response = formationService.createFormation(request);
            if (response.isSuccess()) {
                log.info("Formation créée avec succès");
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                log.warn("Échec de la création de formation: {}", response.getError());
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            log.error("Erreur lors de la création de formation: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }

    /**
     * Récupérer toutes les formations
     */
    @GetMapping
    @Operation(summary = "Récupérer toutes les formations")
    public ResponseEntity<ApiResponse<List<FormationResponseDTO>>> getAllFormations() {
        try {
            ApiResponse<List<FormationResponseDTO>> response = formationService.getAllFormations();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des formations: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }

    /**
     * Récupérer toutes les formations avec pagination
     */
    @GetMapping("/paginated")
    @Operation(summary = "Récupérer toutes les formations avec pagination")
    public ResponseEntity<ApiResponse<Page<FormationResponseDTO>>> getAllFormationsPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {

        try {
            ApiResponse<Page<FormationResponseDTO>> response =
                    formationService.getAllFormations(page, size, sortBy, sortDirection);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des formations avec pagination: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }

    /**
     * Récupérer une formation par ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une formation par son ID")
    public ResponseEntity<ApiResponse<FormationResponseDTO>> getFormationById(
            @Parameter(description = "ID de la formation", required = true)
            @PathVariable Long id) {

        try {
            ApiResponse<FormationResponseDTO> response = formationService.getFormationById(id);
            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            log.error("Erreur lors de la récupération de la formation {}: ", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }

    /**
     * Mettre à jour une formation avec URLs
     */
    @PutMapping("/{id}/url")
    @Operation(summary = "Mettre à jour une formation avec URLs")
    public ResponseEntity<ApiResponse<FormationResponseDTO>> updateFormationWithUrl(
            @Parameter(description = "ID de la formation", required = true)
            @PathVariable Long id,
            @Valid @RequestBody FormationUpdateDTO request) {

        try {
            log.info("Mise à jour de la formation {} avec URLs", id);
            ApiResponse<FormationResponseDTO> response = formationService.updateFormation(id, request);
            if (response.isSuccess()) {
                log.info("Formation mise à jour avec succès: {}", id);
                return ResponseEntity.ok(response);
            } else {
                log.warn("Échec de la mise à jour de la formation: {}", response.getError());
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            log.error("Erreur lors de la mise à jour de la formation: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }

    /**
     * Mettre à jour une formation avec fichiers
     */
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Mettre à jour une formation avec fichiers")
    public ResponseEntity<ApiResponse<FormationResponseDTO>> updateFormationWithFiles(
            @Parameter(description = "ID de la formation", required = true)
            @PathVariable Long id,
            @ModelAttribute FormationUpdateFileDTO request) {

        try {
            log.info("Mise à jour de la formation {} avec fichiers", id);
            ApiResponse<FormationResponseDTO> response = formationService.updateFormationWithFiles(id, request);
            if (response.isSuccess()) {
                log.info("Formation mise à jour avec succès: {}", id);
                return ResponseEntity.ok(response);
            } else {
                log.warn("Échec de la mise à jour de la formation: {}", response.getError());
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            log.error("Erreur lors de la mise à jour de la formation: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }

    /**
     * Mettre à jour une formation (générique)
     */
    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une formation")
    public ResponseEntity<ApiResponse<FormationResponseDTO>> updateFormation(
            @Parameter(description = "ID de la formation", required = true)
            @PathVariable Long id,
            @Valid @RequestBody FormationUpdateDTO request) {

        try {
            log.info("Mise à jour de la formation {}", id);
            ApiResponse<FormationResponseDTO> response = formationService.updateFormation(id, request);
            if (response.isSuccess()) {
                log.info("Formation mise à jour avec succès: {}", id);
                return ResponseEntity.ok(response);
            } else {
                log.warn("Échec de la mise à jour de la formation: {}", response.getError());
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            log.error("Erreur lors de la mise à jour de la formation: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }

    /**
     * Supprimer une formation
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une formation")
    public ResponseEntity<ApiResponse<String>> deleteFormation(
            @Parameter(description = "ID de la formation", required = true)
            @PathVariable Long id) {

        try {
            log.info("Suppression de la formation {}", id);
            ApiResponse<String> response = formationService.deleteFormation(id);
            if (response.isSuccess()) {
                log.info("Formation supprimée avec succès: {}", id);
                return ResponseEntity.ok(response);
            } else {
                log.warn("Échec de la suppression de la formation: {}", response.getError());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            log.error("Erreur lors de la suppression de la formation: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }

    // === RECHERCHES SPÉCIALISÉES ===

    @GetMapping("/with-french-image")
    @Operation(summary = "Récupérer les formations avec image française")
    public ResponseEntity<ApiResponse<List<FormationResponseDTO>>> getFormationsWithImageFrancais() {
        try {
            return ResponseEntity.ok(formationService.getFormationsWithImageFrancais());
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des formations avec image française: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }

    @GetMapping("/with-arabic-image")
    @Operation(summary = "Récupérer les formations avec image arabe")
    public ResponseEntity<ApiResponse<List<FormationResponseDTO>>> getFormationsWithImageArabe() {
        try {
            return ResponseEntity.ok(formationService.getFormationsWithImageArabe());
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des formations avec image arabe: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }

    @GetMapping("/with-both-images")
    @Operation(summary = "Récupérer les formations avec les deux images (français et arabe)")
    public ResponseEntity<ApiResponse<List<FormationResponseDTO>>> getFormationsWithBothImages() {
        try {
            return ResponseEntity.ok(formationService.getFormationsWithBothImages());
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des formations avec les deux images: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }

    @GetMapping("/without-images")
    @Operation(summary = "Récupérer les formations sans images")
    public ResponseEntity<ApiResponse<List<FormationResponseDTO>>> getFormationsWithoutImages() {
        try {
            return ResponseEntity.ok(formationService.getFormationsWithoutImages());
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des formations sans images: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }

    // === STATISTIQUES ===

    @GetMapping("/stats")
    @Operation(summary = "Obtenir les statistiques des formations")
    public ResponseEntity<ApiResponse<FormationStatsDTO>> getFormationStats() {
        try {
            return ResponseEntity.ok(formationService.getFormationStats());
        } catch (Exception e) {
            log.error("Erreur lors du calcul des statistiques: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }

    // === ENDPOINT DE SANTÉ ===

    @GetMapping("/health")
    @Operation(summary = "Vérifier le statut du service de formation")
    public ResponseEntity<ApiResponse<String>> health() {
        return ResponseEntity.ok(ApiResponse.success("Service de formation opérationnel"));
    }
}

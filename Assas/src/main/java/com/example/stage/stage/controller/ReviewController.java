package com.example.stage.stage.controller;

import com.example.stage.stage.dto.*;
import com.example.stage.stage.response.ApiResponse;
import com.example.stage.stage.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
@Tag(name = "Reviews", description = "API de gestion des avis et évaluations - Ouvert à tous")
public class ReviewController {

    private final ReviewService reviewService;

    // === CRÉATION D'AVIS (OUVERT À TOUS) ===

    /**
     * NOUVELLE VERSION: Créer un avis (ouvert à tous avec email)
     */
    @PostMapping
    @Operation(
            summary = "Créer un avis",
            description = "Permet à n'importe qui de créer un avis en fournissant un email. Aucune authentification requise."
    )
    public ResponseEntity<ApiResponse<ReviewResponseDTO>> createReview(
            @Valid @RequestBody ReviewCreateDTO request) {

        try {
            log.info("Création d'un avis pour le professionnel {} par l'email {}",
                    request.getProfessionalId(), request.getClientEmail());

            ApiResponse<ReviewResponseDTO> response = reviewService.createReview(request);

            if (response.isSuccess()) {
                log.info("Avis créé avec succès");
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                log.warn("Échec de la création d'avis: {}", response.getError());
                return ResponseEntity.badRequest().body(response);
            }

        } catch (Exception e) {
            log.error("Erreur lors de la création d'avis: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }

    // === VÉRIFICATION D'ÉLIGIBILITÉ ===

    /**
     * Vérifier si un email peut donner un avis
     */
    @GetMapping("/eligibility/{professionalId}")
    @Operation(
            summary = "Vérifier l'éligibilité pour donner un avis",
            description = "Vérifie si un email peut donner un avis à un professionnel"
    )
    public ResponseEntity<ApiResponse<ReviewEligibilityDTO>> checkReviewEligibility(
            @Parameter(description = "ID du professionnel", required = true)
            @PathVariable Long professionalId,
            @Parameter(description = "Email à vérifier", required = true)
            @RequestParam String email) {

        try {
            log.info("Vérification d'éligibilité pour email: {} et professionnel: {}", email, professionalId);

            ApiResponse<ReviewEligibilityDTO> response =
                    reviewService.canEmailReview(professionalId, email);

            if (response.isSuccess()) {
                log.info("Éligibilité vérifiée avec succès");
                return ResponseEntity.ok(response);
            } else {
                log.warn("Échec de la vérification d'éligibilité: {}", response.getError());
                return ResponseEntity.badRequest().body(response);
            }

        } catch (Exception e) {
            log.error("Erreur lors de la vérification d'éligibilité: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }

    // === CONSULTATION D'AVIS ===

    /**
     * Obtenir tous les avis avec pagination
     */
    @GetMapping
    @Operation(summary = "Récupérer tous les avis avec pagination")
    public ResponseEntity<ApiResponse<Page<ReviewResponseDTO>>> getAllReviews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {

        try {
            ApiResponse<Page<ReviewResponseDTO>> response =
                    reviewService.getAllReviews(page, size, sortBy, sortDirection);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération de tous les avis: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }

    /**
     * Obtenir un avis par ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un avis par son ID")
    public ResponseEntity<ApiResponse<ReviewResponseDTO>> getReviewById(@PathVariable Long id) {
        try {
            ApiResponse<ReviewResponseDTO> response = reviewService.getReviewById(id);
            return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                    .body(response);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération de l'avis {}: ", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }

    /**
     * Obtenir les avis d'un professionnel
     */
    @GetMapping("/professional/{professionalId}")
    @Operation(summary = "Récupérer les avis d'un professionnel")
    public ResponseEntity<ApiResponse<Page<ReviewResponseDTO>>> getReviewsByProfessional(
            @PathVariable Long professionalId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        try {
            ApiResponse<Page<ReviewResponseDTO>> response =
                    reviewService.getReviewsByProfessional(professionalId, page, size);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des avis du professionnel {}: ", professionalId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }

    /**
     * Obtenir les avis par email
     */
    @GetMapping("/by-email")
    @Operation(
            summary = "Récupérer les avis par email",
            description = "Récupère tous les avis donnés par un email spécifique"
    )
    public ResponseEntity<ApiResponse<Page<ReviewResponseDTO>>> getReviewsByEmail(
            @Parameter(description = "Email à rechercher", required = true)
            @RequestParam String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        try {
            log.info("Récupération des avis pour l'email: {}", email);

            ApiResponse<Page<ReviewResponseDTO>> response =
                    reviewService.getReviewsByEmail(email, page, size);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des avis par email: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }

    // === MODIFICATION D'AVIS ===

    /**
     * Mettre à jour un avis par email
     */
    @PutMapping("/{id}")
    @Operation(
            summary = "Mettre à jour un avis",
            description = "Permet de modifier un avis en fournissant l'email utilisé lors de la création"
    )
    public ResponseEntity<ApiResponse<ReviewResponseDTO>> updateReview(
            @PathVariable Long id,
            @Valid @RequestBody ReviewUpdateDTO request) {

        try {
            log.info("Mise à jour de l'avis {} par email: {}", id, request.getClientEmail());

            ApiResponse<ReviewResponseDTO> response =
                    reviewService.updateReview(id, request);

            return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                    .body(response);
        } catch (Exception e) {
            log.error("Erreur lors de la mise à jour de l'avis: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }

    // === SUPPRESSION D'AVIS ===

    /**
     * Supprimer un avis par email
     */
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Supprimer un avis",
            description = "Permet de supprimer un avis en fournissant l'email utilisé lors de la création"
    )
    public ResponseEntity<ApiResponse<String>> deleteReview(
            @PathVariable Long id,
            @Valid @RequestBody ReviewDeleteDTO request) {

        try {
            log.info("Suppression de l'avis {} par email: {}", id, request.getClientEmail());

            ApiResponse<String> response =
                    reviewService.deleteReview(id, request);

            return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                    .body(response);
        } catch (Exception e) {
            log.error("Erreur lors de la suppression de l'avis: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }

    // === RECHERCHE ET STATISTIQUES ===

    /**
     * Recherche avancée d'avis
     */
    @PostMapping("/search")
    @Operation(summary = "Recherche avancée d'avis avec filtres")
    public ResponseEntity<ApiResponse<Page<ReviewResponseDTO>>> searchReviews(
            @RequestBody ReviewSearchDTO searchDTO) {

        try {
            ApiResponse<Page<ReviewResponseDTO>> response = reviewService.searchReviews(searchDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erreur lors de la recherche d'avis: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }

    /**
     * Recherche simple par paramètres GET
     */
    @GetMapping("/search")
    @Operation(summary = "Recherche simple d'avis par paramètres")
    public ResponseEntity<ApiResponse<Page<ReviewResponseDTO>>> searchReviewsByParams(
            @RequestParam(required = false) Long professionalId,
            @RequestParam(required = false) String clientEmail,
            @RequestParam(required = false) Integer rating,
            @RequestParam(required = false) String comment,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {

        try {
            ReviewSearchDTO searchDTO = new ReviewSearchDTO();
            searchDTO.setProfessionalId(professionalId);
            searchDTO.setClientEmail(clientEmail);
            searchDTO.setRating(rating);
            searchDTO.setComment(comment);
            searchDTO.setPage(page);
            searchDTO.setSize(size);
            searchDTO.setSortBy(sortBy);
            searchDTO.setSortDirection(sortDirection);

            ApiResponse<Page<ReviewResponseDTO>> response = reviewService.searchReviews(searchDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erreur lors de la recherche d'avis par paramètres: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }

    /**
     * Obtenir les statistiques d'un professionnel
     */
    @GetMapping("/stats/professional/{professionalId}")
    @Operation(summary = "Récupérer les statistiques d'avis d'un professionnel")
    public ResponseEntity<ApiResponse<ReviewStatsDTO>> getProfessionalReviewStats(
            @PathVariable Long professionalId) {

        try {
            ApiResponse<ReviewStatsDTO> response = reviewService.getProfessionalReviewStats(professionalId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des statistiques: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }

    /**
     * Obtenir les professionnels les mieux notés
     */
    @GetMapping("/top-rated")
    @Operation(summary = "Récupérer les professionnels les mieux notés")
    public ResponseEntity<ApiResponse<List<ProfessionalRatingDTO>>> getTopRatedProfessionals(
            @RequestParam(defaultValue = "5") int minReviews,
            @RequestParam(defaultValue = "10") int limit) {

        try {
            ApiResponse<List<ProfessionalRatingDTO>> response =
                    reviewService.getTopRatedProfessionals(minReviews, limit);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des professionnels les mieux notés: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }

    /**
     * Obtenir les avis par rating
     */
    @GetMapping("/by-rating/{rating}")
    @Operation(summary = "Récupérer les avis par note")
    public ResponseEntity<ApiResponse<Page<ReviewResponseDTO>>> getReviewsByRating(
            @PathVariable Integer rating,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        try {
            if (rating < 1 || rating > 5) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Le rating doit être entre 1 et 5"));
            }

            ReviewSearchDTO searchDTO = new ReviewSearchDTO();
            searchDTO.setRating(rating);
            searchDTO.setPage(page);
            searchDTO.setSize(size);

            ApiResponse<Page<ReviewResponseDTO>> response = reviewService.searchReviews(searchDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des avis par rating: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }

    /**
     * Obtenir les avis récents
     */
    @GetMapping("/recent")
    @Operation(summary = "Récupérer les avis récents")
    public ResponseEntity<ApiResponse<Page<ReviewResponseDTO>>> getRecentReviews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        try {
            ReviewSearchDTO searchDTO = new ReviewSearchDTO();
            searchDTO.setPage(page);
            searchDTO.setSize(size);
            searchDTO.setSortBy("createdAt");
            searchDTO.setSortDirection("DESC");

            ApiResponse<Page<ReviewResponseDTO>> response = reviewService.searchReviews(searchDTO);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des avis récents: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }

    /**
     * Endpoint de santé pour le service d'avis
     */
    @GetMapping("/health")
    @Operation(summary = "Vérifier le statut du service d'avis")
    public ResponseEntity<ApiResponse<String>> health() {
        return ResponseEntity.ok(ApiResponse.success("Service d'avis opérationnel - Ouvert à tous"));
    }
}
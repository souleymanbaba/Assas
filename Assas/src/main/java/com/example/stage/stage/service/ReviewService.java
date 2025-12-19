package com.example.stage.stage.service;

import com.example.stage.stage.dto.*;
import com.example.stage.stage.entity.Review;
import com.example.stage.stage.entity.Professional;
import com.example.stage.stage.repository.ReviewRepository;
import com.example.stage.stage.repository.ProfessionalRepository;
import com.example.stage.stage.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProfessionalRepository professionalRepository;

    // Pattern pour valider l'email
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$"
    );

    /**
     * NOUVELLE VERSION SIMPLIFIÉE: Créer un avis avec email direct
     */
    @Transactional
    public ApiResponse<ReviewResponseDTO> createReview(ReviewCreateDTO request) {
        try {
            log.info("Création d'un avis pour le professionnel {} par l'email {}",
                    request.getProfessionalId(), request.getClientEmail());

            // Valider l'email
            if (request.getClientEmail() == null || request.getClientEmail().trim().isEmpty()) {
                return ApiResponse.error("L'email est obligatoire");
            }

            String clientEmail = request.getClientEmail().trim().toLowerCase();

            if (!EMAIL_PATTERN.matcher(clientEmail).matches()) {
                return ApiResponse.error("Format d'email invalide");
            }

            // Vérifier si le professionnel existe
            Optional<Professional> professionalOpt = professionalRepository.findById(request.getProfessionalId());
            if (professionalOpt.isEmpty()) {
                return ApiResponse.error("Professionnel non trouvé");
            }

            Professional professional = professionalOpt.get();

            // Vérifier si cet email a déjà donné un avis à ce professionnel
            if (reviewRepository.existsByProfessionalIdAndClientEmail(request.getProfessionalId(), clientEmail)) {
                return ApiResponse.error("Un avis a déjà été donné par cet email pour ce professionnel");
            }

            // Vérifier que le professionnel ne peut pas s'auto-évaluer
            if (professional.getUser() != null &&
                    professional.getUser().getEmail().equalsIgnoreCase(clientEmail)) {
                return ApiResponse.error("Vous ne pouvez pas vous évaluer vous-même");
            }

            // Créer l'avis
            Review review = new Review();
            review.setProfessional(professional);
            review.setClientEmail(clientEmail);
            review.setClientName(request.getClientName());
            review.setRating(request.getRating());
            review.setComment(request.getComment());

            review = reviewRepository.save(review);
            log.info("Avis créé avec l'ID: {} pour le professionnel: {}", review.getId(), professional.getId());

            // Mettre à jour les statistiques du professionnel
            updateProfessionalRatingStats(professional.getId());

            return ApiResponse.success("Avis créé avec succès", new ReviewResponseDTO(review));

        } catch (Exception e) {
            log.error("Erreur lors de la création de l'avis: ", e);
            return ApiResponse.error("Erreur lors de la création: " + e.getMessage());
        }
    }

    /**
     * Vérifier si un email peut donner un avis
     */
    public ApiResponse<ReviewEligibilityDTO> canEmailReview(Long professionalId, String email) {
        try {
            log.info("Vérification de l'éligibilité pour l'email: {} et professionnel: {}", email, professionalId);

            ReviewEligibilityDTO eligibility = new ReviewEligibilityDTO();
            eligibility.setProfessionalId(professionalId);
            eligibility.setEmail(email);

            // Valider l'email
            if (email == null || email.trim().isEmpty()) {
                eligibility.setCanReview(false);
                eligibility.setMessage("L'email est obligatoire");
                return ApiResponse.success("Éligibilité vérifiée", eligibility);
            }

            String cleanEmail = email.trim().toLowerCase();

            if (!EMAIL_PATTERN.matcher(cleanEmail).matches()) {
                eligibility.setCanReview(false);
                eligibility.setMessage("Format d'email invalide");
                return ApiResponse.success("Éligibilité vérifiée", eligibility);
            }

            // Vérifier si le professionnel existe
            Optional<Professional> professionalOpt = professionalRepository.findById(professionalId);
            if (professionalOpt.isEmpty()) {
                eligibility.setCanReview(false);
                eligibility.setMessage("Professionnel non trouvé");
                return ApiResponse.success("Éligibilité vérifiée", eligibility);
            }

            Professional professional = professionalOpt.get();

            // Vérifier que le professionnel ne peut pas s'auto-évaluer
            if (professional.getUser() != null &&
                    professional.getUser().getEmail().equalsIgnoreCase(cleanEmail)) {
                eligibility.setCanReview(false);
                eligibility.setMessage("Vous ne pouvez pas vous évaluer vous-même");
                return ApiResponse.success("Éligibilité vérifiée", eligibility);
            }

            // Vérifier si l'email a déjà donné un avis
            boolean hasExistingReview = reviewRepository.existsByProfessionalIdAndClientEmail(professionalId, cleanEmail);
            eligibility.setHasExistingReview(hasExistingReview);

            if (hasExistingReview) {
                eligibility.setCanReview(false);
                eligibility.setMessage("Vous avez déjà donné un avis à ce professionnel");
            } else {
                eligibility.setCanReview(true);
                eligibility.setMessage("Vous pouvez donner un avis à ce professionnel");
            }

            return ApiResponse.success("Éligibilité vérifiée avec succès", eligibility);

        } catch (Exception e) {
            log.error("Erreur lors de la vérification d'éligibilité: ", e);
            return ApiResponse.error("Erreur lors de la vérification: " + e.getMessage());
        }
    }

    /**
     * Obtenir les avis par email
     */
    public ApiResponse<Page<ReviewResponseDTO>> getReviewsByEmail(String email, int page, int size) {
        try {
            log.info("Récupération des avis pour l'email: {}", email);

            String cleanEmail = email.trim().toLowerCase();
            Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
            Page<Review> reviews = reviewRepository.findByClientEmailOrderByCreatedAtDesc(cleanEmail, pageable);

            Page<ReviewResponseDTO> response = reviews.map(ReviewResponseDTO::new);

            return ApiResponse.success("Avis récupérés avec succès", response);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des avis par email: ", e);
            return ApiResponse.error("Erreur lors de la récupération: " + e.getMessage());
        }
    }

    /**
     * Mettre à jour un avis par email
     */
    @Transactional
    public ApiResponse<ReviewResponseDTO> updateReview(Long reviewId, ReviewUpdateDTO request) {
        try {
            log.info("Mise à jour de l'avis {} par l'email: {}", reviewId, request.getClientEmail());

            Optional<Review> reviewOpt = reviewRepository.findById(reviewId);
            if (reviewOpt.isEmpty()) {
                return ApiResponse.error("Avis non trouvé");
            }

            Review review = reviewOpt.get();
            String cleanEmail = request.getClientEmail().trim().toLowerCase();

            // Vérifier que l'email correspond à celui qui a créé l'avis
            if (!review.getClientEmail().equalsIgnoreCase(cleanEmail)) {
                return ApiResponse.error("Vous ne pouvez modifier que vos propres avis");
            }

            // Mettre à jour les champs
            review.setClientName(request.getClientName());
            review.setRating(request.getRating());
            review.setComment(request.getComment());

            review = reviewRepository.save(review);

            // Mettre à jour les statistiques du professionnel
            updateProfessionalRatingStats(review.getProfessional().getId());

            log.info("Avis mis à jour avec succès: {}", reviewId);
            return ApiResponse.success("Avis mis à jour avec succès", new ReviewResponseDTO(review));

        } catch (Exception e) {
            log.error("Erreur lors de la mise à jour de l'avis: ", e);
            return ApiResponse.error("Erreur lors de la mise à jour: " + e.getMessage());
        }
    }

    /**
     * Supprimer un avis par email
     */
    @Transactional
    public ApiResponse<String> deleteReview(Long reviewId, ReviewDeleteDTO request) {
        try {
            log.info("Suppression de l'avis {} par l'email: {}", reviewId, request.getClientEmail());

            Optional<Review> reviewOpt = reviewRepository.findById(reviewId);
            if (reviewOpt.isEmpty()) {
                return ApiResponse.error("Avis non trouvé");
            }

            Review review = reviewOpt.get();
            String cleanEmail = request.getClientEmail().trim().toLowerCase();

            // Vérifier que l'email correspond à celui qui a créé l'avis
            if (!review.getClientEmail().equalsIgnoreCase(cleanEmail)) {
                return ApiResponse.error("Vous ne pouvez supprimer que vos propres avis");
            }

            Long professionalId = review.getProfessional().getId();

            reviewRepository.delete(review);

            // Mettre à jour les statistiques du professionnel
            updateProfessionalRatingStats(professionalId);

            log.info("Avis supprimé avec succès: {}", reviewId);
            return ApiResponse.success("Avis supprimé avec succès");

        } catch (Exception e) {
            log.error("Erreur lors de la suppression de l'avis: ", e);
            return ApiResponse.error("Erreur lors de la suppression: " + e.getMessage());
        }
    }

    // === MÉTHODES DE CONSULTATION ===

    /**
     * Obtenir tous les avis avec pagination
     */
    public ApiResponse<Page<ReviewResponseDTO>> getAllReviews(int page, int size, String sortBy, String sortDirection) {
        try {
            Sort sort = sortDirection.equalsIgnoreCase("ASC") ?
                    Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

            Pageable pageable = PageRequest.of(page, size, sort);
            Page<Review> reviews = reviewRepository.findAll(pageable);

            Page<ReviewResponseDTO> response = reviews.map(ReviewResponseDTO::new);

            return ApiResponse.success("Avis récupérés avec succès", response);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des avis: ", e);
            return ApiResponse.error("Erreur lors de la récupération: " + e.getMessage());
        }
    }

    /**
     * Obtenir un avis par ID
     */
    public ApiResponse<ReviewResponseDTO> getReviewById(Long id) {
        try {
            Optional<Review> reviewOpt = reviewRepository.findById(id);

            if (reviewOpt.isEmpty()) {
                return ApiResponse.error("Avis non trouvé");
            }

            return ApiResponse.success("Avis récupéré avec succès", new ReviewResponseDTO(reviewOpt.get()));
        } catch (Exception e) {
            log.error("Erreur lors de la récupération de l'avis: ", e);
            return ApiResponse.error("Erreur lors de la récupération: " + e.getMessage());
        }
    }

    /**
     * Obtenir les avis d'un professionnel
     */
    public ApiResponse<Page<ReviewResponseDTO>> getReviewsByProfessional(Long professionalId, int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
            Page<Review> reviews = reviewRepository.findByProfessionalIdOrderByCreatedAtDesc(professionalId, pageable);

            Page<ReviewResponseDTO> response = reviews.map(ReviewResponseDTO::new);

            return ApiResponse.success("Avis du professionnel récupérés avec succès", response);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des avis du professionnel: ", e);
            return ApiResponse.error("Erreur lors de la récupération: " + e.getMessage());
        }
    }

    /**
     * Recherche avancée d'avis
     */
    public ApiResponse<Page<ReviewResponseDTO>> searchReviews(ReviewSearchDTO searchDTO) {
        try {
            Sort sort = searchDTO.getSortDirection().equalsIgnoreCase("ASC") ?
                    Sort.by(searchDTO.getSortBy()).ascending() :
                    Sort.by(searchDTO.getSortBy()).descending();

            Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize(), sort);

            Page<Review> reviews = reviewRepository.searchReviews(
                    searchDTO.getProfessionalId(),
                    searchDTO.getClientEmail(),
                    searchDTO.getRating(),
                    searchDTO.getStartDate(),
                    searchDTO.getEndDate(),
                    searchDTO.getComment(),
                    pageable
            );

            Page<ReviewResponseDTO> response = reviews.map(ReviewResponseDTO::new);

            return ApiResponse.success("Recherche effectuée avec succès", response);
        } catch (Exception e) {
            log.error("Erreur lors de la recherche d'avis: ", e);
            return ApiResponse.error("Erreur lors de la recherche: " + e.getMessage());
        }
    }

    /**
     * Obtenir les statistiques d'un professionnel
     */
    public ApiResponse<ReviewStatsDTO> getProfessionalReviewStats(Long professionalId) {
        try {
            ReviewStatsDTO stats = new ReviewStatsDTO();

            // Statistiques de base
            stats.setTotalReviews(reviewRepository.countByProfessionalId(professionalId));
            Double avgRating = reviewRepository.calculateAverageRatingByProfessionalId(professionalId);
            stats.setAverageRating(avgRating != null ? avgRating : 0.0);

            // Distribution des ratings
            List<Object[]> distribution = reviewRepository.getRatingStatsByProfessionalId(professionalId);
            Map<Integer, Long> ratingMap = new HashMap<>();

            for (Object[] row : distribution) {
                Integer rating = (Integer) row[0];
                Long count = (Long) row[1];
                ratingMap.put(rating, count);
            }

            stats.setRatingDistribution(ratingMap);
            stats.setReviewsCount1Star(ratingMap.getOrDefault(1, 0L));
            stats.setReviewsCount2Star(ratingMap.getOrDefault(2, 0L));
            stats.setReviewsCount3Star(ratingMap.getOrDefault(3, 0L));
            stats.setReviewsCount4Star(ratingMap.getOrDefault(4, 0L));
            stats.setReviewsCount5Star(ratingMap.getOrDefault(5, 0L));

            return ApiResponse.success("Statistiques récupérées avec succès", stats);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des statistiques: ", e);
            return ApiResponse.error("Erreur lors de la récupération des statistiques: " + e.getMessage());
        }
    }

    /**
     * Obtenir les professionnels les mieux notés
     */
    public ApiResponse<List<ProfessionalRatingDTO>> getTopRatedProfessionals(int minReviews, int limit) {
        try {
            List<Object[]> topRated = reviewRepository.findTopRatedProfessionals(minReviews);

            List<ProfessionalRatingDTO> result = topRated.stream()
                    .limit(limit)
                    .map(row -> {
                        Long professionalId = (Long) row[0];
                        Double avgRating = (Double) row[1];
                        Long reviewCount = (Long) row[2];

                        Optional<Professional> professionalOpt = professionalRepository.findById(professionalId);

                        ProfessionalRatingDTO dto = new ProfessionalRatingDTO();
                        dto.setProfessionalId(professionalId);
                        dto.setProfessionalName(professionalOpt.isPresent() ?
                                professionalOpt.get().getFirstName() + " " + professionalOpt.get().getLastName() : "Inconnu");
                        dto.setAverageRating(avgRating);
                        dto.setReviewCount(reviewCount);

                        // Ajouter les statistiques détaillées
                        ApiResponse<ReviewStatsDTO> statsResponse = getProfessionalReviewStats(professionalId);
                        if (statsResponse.isSuccess()) {
                            dto.setStats(statsResponse.getData());
                        }

                        return dto;
                    })
                    .collect(Collectors.toList());

            return ApiResponse.success("Professionnels les mieux notés récupérés", result);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des meilleurs professionnels: ", e);
            return ApiResponse.error("Erreur lors de la récupération: " + e.getMessage());
        }
    }

    /**
     * Mettre à jour les statistiques du professionnel
     */
    private void updateProfessionalRatingStats(Long professionalId) {
        try {
            Optional<Professional> professionalOpt = professionalRepository.findById(professionalId);
            if (professionalOpt.isPresent()) {
                Professional professional = professionalOpt.get();
                Double avgRating = reviewRepository.calculateAverageRatingByProfessionalId(professionalId);
                Long reviewCount = reviewRepository.countByProfessionalId(professionalId);

                // TODO: Mettre à jour les champs dans l'entité Professional si ils existent
                // professional.setRatingAverage(avgRating);
                // professional.setRatingCount(reviewCount.intValue());
                // professionalRepository.save(professional);

                log.info("Statistiques mises à jour pour le professionnel {}: {} avis, moyenne {}",
                        professionalId, reviewCount, avgRating);
            }
        } catch (Exception e) {
            log.warn("Erreur lors de la mise à jour des statistiques pour le professionnel {}: {}",
                    professionalId, e.getMessage());
        }
    }
}
package com.example.stage.stage.service;

import com.example.stage.stage.dto.*;
import com.example.stage.stage.entity.Formation;
import com.example.stage.stage.repository.FormationRepository;
import com.example.stage.stage.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FormationService {

    private final FormationRepository formationRepository;
    private final FileService fileService;

    /**
     * Créer une nouvelle formation (méthode originale avec URLs)
     */
    @Transactional
    public ApiResponse<FormationResponseDTO> createFormation(FormationCreateDTO request) {
        try {
            log.info("Création d'une nouvelle formation avec URLs");

            Formation formation = new Formation();
            formation.setImageFrancais(request.getImageFrancais());
            formation.setImageArabe(request.getImageArabe());
            formation.setEndDate(request.getEndDate());

            formation = formationRepository.save(formation);
            log.info("Formation créée avec l'ID: {}", formation.getId());

            return ApiResponse.success("Formation créée avec succès", new FormationResponseDTO(formation));

        } catch (Exception e) {
            log.error("Erreur lors de la création de la formation: ", e);
            return ApiResponse.error("Erreur lors de la création: " + e.getMessage());
        }
    }

    /**
     * Créer une nouvelle formation avec fichiers
     */
    @Transactional
    public ApiResponse<FormationResponseDTO> createFormationWithFiles(FormationCreateFileDTO request) {
        try {
            log.info("Création d'une nouvelle formation avec fichiers");

            Formation formation = new Formation();

            // Gérer l'image française
            if (request.getImageFrancais() != null && !request.getImageFrancais().isEmpty()) {
                String imageUrl = fileService.saveImage(request.getImageFrancais(), "francais");
                formation.setImageFrancais(imageUrl);
            } else if (request.getImageFrancaisUrl() != null && !request.getImageFrancaisUrl().trim().isEmpty()) {
                formation.setImageFrancais(request.getImageFrancaisUrl());
            }

            // Gérer l'image arabe
            if (request.getImageArabe() != null && !request.getImageArabe().isEmpty()) {
                String imageUrl = fileService.saveImage(request.getImageArabe(), "arabe");
                formation.setImageArabe(imageUrl);
            } else if (request.getImageArabeUrl() != null && !request.getImageArabeUrl().trim().isEmpty()) {
                formation.setImageArabe(request.getImageArabeUrl());
            }
formation.setEndDate(request.getEndDate());
            formation = formationRepository.save(formation);
            log.info("Formation créée avec l'ID: {}", formation.getId());

            return ApiResponse.success("Formation créée avec succès", new FormationResponseDTO(formation));

        } catch (Exception e) {
            log.error("Erreur lors de la création de la formation: ", e);
            return ApiResponse.error("Erreur lors de la création: " + e.getMessage());
        }
    }

    /**
     * Mettre à jour une formation (méthode originale avec URLs)
     */
    @Transactional
    public ApiResponse<FormationResponseDTO> updateFormation(Long id, FormationUpdateDTO request) {
        try {
            log.info("Mise à jour de la formation avec l'ID: {}", id);

            Optional<Formation> formationOpt = formationRepository.findById(id);
            if (formationOpt.isEmpty()) {
                return ApiResponse.error("Formation non trouvée avec l'ID: " + id);
            }

            Formation formation = formationOpt.get();

            // Mettre à jour les champs si fournis
            if (request.getImageFrancais() != null) {
                formation.setImageFrancais(request.getImageFrancais());
            }
            if (request.getImageArabe() != null) {
                formation.setImageArabe(request.getImageArabe());
            }

            formation.setEndDate(request.getEndDate());

            formation = formationRepository.save(formation);
            log.info("Formation mise à jour avec succès: {}", id);

            return ApiResponse.success("Formation mise à jour avec succès", new FormationResponseDTO(formation));

        } catch (Exception e) {
            log.error("Erreur lors de la mise à jour de la formation: ", e);
            return ApiResponse.error("Erreur lors de la mise à jour: " + e.getMessage());
        }
    }

    /**
     * Mettre à jour une formation avec fichiers
     */
    @Transactional
    public ApiResponse<FormationResponseDTO> updateFormationWithFiles(Long id, FormationUpdateFileDTO request) {
        try {
            log.info("Mise à jour de la formation avec l'ID: {}", id);

            Optional<Formation> formationOpt = formationRepository.findById(id);
            if (formationOpt.isEmpty()) {
                return ApiResponse.error("Formation non trouvée avec l'ID: " + id);
            }

            Formation formation = formationOpt.get();

            // Gérer l'image française
            if (request.isDeleteImageFrancais()) {
                // Supprimer l'ancien fichier si c'est un fichier local
                if (formation.getImageFrancais() != null && formation.getImageFrancais().startsWith("/api/files/")) {
                    fileService.deleteImage(formation.getImageFrancais());
                }
                formation.setImageFrancais(null);
            } else if (request.getImageFrancais() != null && !request.getImageFrancais().isEmpty()) {
                // Supprimer l'ancien fichier si c'est un fichier local
                if (formation.getImageFrancais() != null && formation.getImageFrancais().startsWith("/api/files/")) {
                    fileService.deleteImage(formation.getImageFrancais());
                }
                // Sauvegarder le nouveau fichier
                String imageUrl = fileService.saveImage(request.getImageFrancais(), "francais");
                formation.setImageFrancais(imageUrl);
            } else if (request.getImageFrancaisUrl() != null && !request.getImageFrancaisUrl().trim().isEmpty()) {
                // Supprimer l'ancien fichier si c'est un fichier local
                if (formation.getImageFrancais() != null && formation.getImageFrancais().startsWith("/api/files/")) {
                    fileService.deleteImage(formation.getImageFrancais());
                }
                formation.setImageFrancais(request.getImageFrancaisUrl());
            }

            // Gérer l'image arabe
            if (request.isDeleteImageArabe()) {
                // Supprimer l'ancien fichier si c'est un fichier local
                if (formation.getImageArabe() != null && formation.getImageArabe().startsWith("/api/files/")) {
                    fileService.deleteImage(formation.getImageArabe());
                }
                formation.setImageArabe(null);
            } else if (request.getImageArabe() != null && !request.getImageArabe().isEmpty()) {
                // Supprimer l'ancien fichier si c'est un fichier local
                if (formation.getImageArabe() != null && formation.getImageArabe().startsWith("/api/files/")) {
                    fileService.deleteImage(formation.getImageArabe());
                }
                // Sauvegarder le nouveau fichier
                String imageUrl = fileService.saveImage(request.getImageArabe(), "arabe");
                formation.setImageArabe(imageUrl);
            } else if (request.getImageArabeUrl() != null && !request.getImageArabeUrl().trim().isEmpty()) {
                // Supprimer l'ancien fichier si c'est un fichier local
                if (formation.getImageArabe() != null && formation.getImageArabe().startsWith("/api/files/")) {
                    fileService.deleteImage(formation.getImageArabe());
                }
                formation.setImageArabe(request.getImageArabeUrl());
            }
            formation.setEndDate(request.getEndDate());

            formation = formationRepository.save(formation);
            log.info("Formation mise à jour avec succès: {}", id);

            return ApiResponse.success("Formation mise à jour avec succès", new FormationResponseDTO(formation));

        } catch (Exception e) {
            log.error("Erreur lors de la mise à jour de la formation: ", e);
            return ApiResponse.error("Erreur lors de la mise à jour: " + e.getMessage());
        }
    }

    /**
     * Supprimer une formation avec nettoyage des fichiers
     */
    @Transactional
    public ApiResponse<String> deleteFormation(Long id) {
        try {
            log.info("Suppression de la formation avec l'ID: {}", id);

            Optional<Formation> formationOpt = formationRepository.findById(id);
            if (formationOpt.isEmpty()) {
                return ApiResponse.error("Formation non trouvée avec l'ID: " + id);
            }

            Formation formation = formationOpt.get();

            // Supprimer les fichiers associés s'ils existent
            if (formation.getImageFrancais() != null && formation.getImageFrancais().startsWith("/api/files/")) {
                fileService.deleteImage(formation.getImageFrancais());
            }
            if (formation.getImageArabe() != null && formation.getImageArabe().startsWith("/api/files/")) {
                fileService.deleteImage(formation.getImageArabe());
            }

            formationRepository.deleteById(id);
            log.info("Formation supprimée avec succès: {}", id);

            return ApiResponse.success("Formation supprimée avec succès");

        } catch (Exception e) {
            log.error("Erreur lors de la suppression de la formation: ", e);
            return ApiResponse.error("Erreur lors de la suppression: " + e.getMessage());
        }
    }

    public ApiResponse<List<FormationResponseDTO>> getFormationsNonExpirees() {
        try {
            log.info("Récupération des formations non expirées");
            List<Formation> formations = formationRepository
                    .findByEndDateIsNullOrEndDateAfterOrderByCreatedAtDesc(LocalDateTime.now());

            List<FormationResponseDTO> formationDTOs = formations.stream()
                    .map(FormationResponseDTO::new)
                    .collect(Collectors.toList());

            return ApiResponse.success("Formations valides récupérées", formationDTOs);

        } catch (Exception e) {
            log.error("Erreur lors de la récupération des formations valides: ", e);
            return ApiResponse.error("Erreur : " + e.getMessage());
        }
    }


    // Les autres méthodes restent inchangées...
    public ApiResponse<List<FormationResponseDTO>> getAllFormations() {
        try {
            log.info("Récupération de toutes les formations");

            List<Formation> formations = formationRepository.findAllByOrderByCreatedAtDesc();
            List<FormationResponseDTO> formationDTOs = formations.stream()
                    .map(FormationResponseDTO::new)
                    .collect(Collectors.toList());

            return ApiResponse.success("Formations récupérées avec succès", formationDTOs);

        } catch (Exception e) {
            log.error("Erreur lors de la récupération des formations: ", e);
            return ApiResponse.error("Erreur lors de la récupération: " + e.getMessage());
        }
    }

    public ApiResponse<Page<FormationResponseDTO>> getAllFormations(int page, int size, String sortBy, String sortDirection) {
        try {
            log.info("Récupération des formations avec pagination - page: {}, size: {}", page, size);

            Sort sort = sortDirection.equalsIgnoreCase("ASC") ?
                    Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

            Pageable pageable = PageRequest.of(page, size, sort);
            Page<Formation> formations = formationRepository.findAll(pageable);

            Page<FormationResponseDTO> response = formations.map(FormationResponseDTO::new);

            return ApiResponse.success("Formations récupérées avec succès", response);

        } catch (Exception e) {
            log.error("Erreur lors de la récupération des formations avec pagination: ", e);
            return ApiResponse.error("Erreur lors de la récupération: " + e.getMessage());
        }
    }

    public ApiResponse<FormationResponseDTO> getFormationById(Long id) {
        try {
            log.info("Récupération de la formation avec l'ID: {}", id);

            Optional<Formation> formationOpt = formationRepository.findById(id);
            if (formationOpt.isEmpty()) {
                return ApiResponse.error("Formation non trouvée avec l'ID: " + id);
            }

            Formation formation = formationOpt.get();
            return ApiResponse.success("Formation récupérée avec succès", new FormationResponseDTO(formation));

        } catch (Exception e) {
            log.error("Erreur lors de la récupération de la formation: ", e);
            return ApiResponse.error("Erreur lors de la récupération: " + e.getMessage());
        }
    }

    public ApiResponse<List<FormationResponseDTO>> getFormationsWithImageFrancais() {
        try {
            log.info("Récupération des formations avec image française");

            List<Formation> formations = formationRepository.findByImageFrancaisIsNotNullOrderByCreatedAtDesc();
            List<FormationResponseDTO> formationDTOs = formations.stream()
                    .map(FormationResponseDTO::new)
                    .collect(Collectors.toList());

            return ApiResponse.success("Formations avec image française récupérées", formationDTOs);

        } catch (Exception e) {
            log.error("Erreur lors de la récupération des formations avec image française: ", e);
            return ApiResponse.error("Erreur lors de la récupération: " + e.getMessage());
        }
    }

    public ApiResponse<List<FormationResponseDTO>> getFormationsWithImageArabe() {
        try {
            log.info("Récupération des formations avec image arabe");

            List<Formation> formations = formationRepository.findByImageArabeIsNotNullOrderByCreatedAtDesc();
            List<FormationResponseDTO> formationDTOs = formations.stream()
                    .map(FormationResponseDTO::new)
                    .collect(Collectors.toList());

            return ApiResponse.success("Formations avec image arabe récupérées", formationDTOs);

        } catch (Exception e) {
            log.error("Erreur lors de la récupération des formations avec image arabe: ", e);
            return ApiResponse.error("Erreur lors de la récupération: " + e.getMessage());
        }
    }

    public ApiResponse<List<FormationResponseDTO>> getFormationsWithBothImages() {
        try {
            log.info("Récupération des formations avec les deux images");

            List<Formation> formations = formationRepository.findFormationsWithBothImages();
            List<FormationResponseDTO> formationDTOs = formations.stream()
                    .map(FormationResponseDTO::new)
                    .collect(Collectors.toList());

            return ApiResponse.success("Formations avec les deux images récupérées", formationDTOs);

        } catch (Exception e) {
            log.error("Erreur lors de la récupération des formations avec les deux images: ", e);
            return ApiResponse.error("Erreur lors de la récupération: " + e.getMessage());
        }
    }

    public ApiResponse<List<FormationResponseDTO>> getFormationsWithoutImages() {
        try {
            log.info("Récupération des formations sans images");

            List<Formation> formations = formationRepository.findFormationsWithoutImages();
            List<FormationResponseDTO> formationDTOs = formations.stream()
                    .map(FormationResponseDTO::new)
                    .collect(Collectors.toList());

            return ApiResponse.success("Formations sans images récupérées", formationDTOs);

        } catch (Exception e) {
            log.error("Erreur lors de la récupération des formations sans images: ", e);
            return ApiResponse.error("Erreur lors de la récupération: " + e.getMessage());
        }
    }

    public ApiResponse<FormationStatsDTO> getFormationStats() {
        try {
            log.info("Calcul des statistiques des formations");

            FormationStatsDTO stats = new FormationStatsDTO();
            stats.setTotalFormations(formationRepository.count());
            stats.setFormationsWithImageFrancais(formationRepository.countByImageFrancaisIsNotNull());
            stats.setFormationsWithImageArabe(formationRepository.countByImageArabeIsNotNull());
            stats.setFormationsWithBothImages(formationRepository.countFormationsWithBothImages());
            stats.setFormationsWithoutImages(stats.getTotalFormations() -
                    (stats.getFormationsWithImageFrancais() + stats.getFormationsWithImageArabe() - stats.getFormationsWithBothImages()));

            return ApiResponse.success("Statistiques calculées avec succès", stats);

        } catch (Exception e) {
            log.error("Erreur lors du calcul des statistiques: ", e);
            return ApiResponse.error("Erreur lors du calcul des statistiques: " + e.getMessage());
        }
    }
}
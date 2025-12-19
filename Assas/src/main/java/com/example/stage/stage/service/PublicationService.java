package com.example.stage.stage.service;

import com.example.stage.stage.dto.*;
import com.example.stage.stage.entity.Publication;
import com.example.stage.stage.repository.PublicationRepository;
import com.example.stage.stage.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicationService {

    private final PublicationRepository publicationRepository;
    private final FileStorageService fileStorageService;

    // CREATE - Créer une nouvelle publication
    @Transactional
    public ApiResponse<PublicationResponseDTO> createPublication(PublicationCreateDTO request) {
        try {
            // Validation des dates
            if (request.getEndDate().isBefore(request.getStartDate())) {
                return ApiResponse.error("La date de fin doit être postérieure à la date de début");
            }

            // Vérifier s'il y a chevauchement de dates pour cet email
            if (publicationRepository.existsOverlappingPublication(
                    request.getEmail(), request.getStartDate(), request.getEndDate())) {
                return ApiResponse.error("Il existe déjà une publication pour cet email dans cette période");
            }

            // Créer la publication
            Publication publication = new Publication();
            publication.setTitle(request.getTitle());
            publication.setEmail(request.getEmail());
            publication.setPlace(request.getPlace());
            publication.setStartDate(request.getStartDate());
            publication.setEndDate(request.getEndDate());

            // Gérer l'upload d'image
            if (request.getImage() != null && !request.getImage().isEmpty()) {
                String imageUrl = fileStorageService.storeFile(request.getImage(), "publications");
                publication.setImageUrl(imageUrl);
            }

            publication = publicationRepository.save(publication);

            return ApiResponse.success(new PublicationResponseDTO(publication));

        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de la création: " + e.getMessage());
        }
    }

    // READ - Obtenir toutes les publications avec pagination
    public ApiResponse<Page<PublicationResponseDTO>> getAllPublications(int page, int size, String sortBy, String sortDirection) {
        try {
            Sort sort = sortDirection.equalsIgnoreCase("ASC") ?
                    Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

            Pageable pageable = PageRequest.of(page, size, sort);
            Page<Publication> publications = publicationRepository.findAll(pageable);

            Page<PublicationResponseDTO> response = publications.map(PublicationResponseDTO::new);

            return ApiResponse.success(response);
        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de la récupération: " + e.getMessage());
        }
    }

    // READ - Obtenir une publication par ID
    public ApiResponse<PublicationResponseDTO> getPublicationById(Long id) {
        try {
            Publication publication = publicationRepository.findById(id)
                    .orElse(null);

            if (publication == null) {
                return ApiResponse.error("Publication non trouvée");
            }

            return ApiResponse.success(new PublicationResponseDTO(publication));
        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de la récupération: " + e.getMessage());
        }
    }

    // READ - Obtenir les publications par email
    public ApiResponse<Page<PublicationResponseDTO>> getPublicationsByEmail(String email, int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
            Page<Publication> publications = publicationRepository.findByEmailOrderByCreatedAtDesc(email, pageable);

            Page<PublicationResponseDTO> response = publications.map(PublicationResponseDTO::new);

            return ApiResponse.success(response);
        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de la récupération: " + e.getMessage());
        }
    }

    // READ - Obtenir les publications actives
    public ApiResponse<Page<PublicationResponseDTO>> getActivePublications(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
            Page<Publication> publications = publicationRepository.findActivePublications(LocalDateTime.now(), pageable);

            Page<PublicationResponseDTO> response = publications.map(PublicationResponseDTO::new);

            return ApiResponse.success(response);
        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de la récupération: " + e.getMessage());
        }
    }

    // READ - Obtenir les publications par place
    public ApiResponse<Page<PublicationResponseDTO>> getPublicationsByPlace(Integer place, int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
            Page<Publication> publications = publicationRepository.findByPlaceOrderByCreatedAtDesc(place, pageable);

            Page<PublicationResponseDTO> response = publications.map(PublicationResponseDTO::new);

            return ApiResponse.success(response);
        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de la récupération: " + e.getMessage());
        }
    }

    // UPDATE - Mettre à jour une publication
    @Transactional
    public ApiResponse<PublicationResponseDTO> updatePublication(Long id, PublicationUpdateDTO request) {
        try {
            Publication publication = publicationRepository.findById(id)
                    .orElse(null);

            if (publication == null) {
                return ApiResponse.error("Publication non trouvée");
            }

            // Validation des dates
            if (request.getEndDate().isBefore(request.getStartDate())) {
                return ApiResponse.error("La date de fin doit être postérieure à la date de début");
            }

            // Vérifier s'il y a chevauchement de dates (exclure l'ID actuel)
            if (publicationRepository.existsOverlappingPublicationForUpdate(
                    request.getEmail(), id, request.getStartDate(), request.getEndDate())) {
                return ApiResponse.error("Il existe déjà une publication pour cet email dans cette période");
            }

            // Mettre à jour les champs
            publication.setTitle(request.getTitle());
            publication.setEmail(request.getEmail());
            publication.setPlace(request.getPlace());
            publication.setStartDate(request.getStartDate());
            publication.setEndDate(request.getEndDate());

            // Gérer l'image
            if (request.getRemoveImage() != null && request.getRemoveImage()) {
                // Supprimer l'image existante
                if (publication.getImageUrl() != null) {
                    fileStorageService.deleteFile(publication.getImageUrl());
                }
                publication.setImageUrl(null);
            }

            if (request.getImage() != null && !request.getImage().isEmpty()) {
                // Supprimer l'ancienne image
                if (publication.getImageUrl() != null) {
                    fileStorageService.deleteFile(publication.getImageUrl());
                }
                // Sauvegarder la nouvelle image
                String imageUrl = fileStorageService.storeFile(request.getImage(), "publications");
                publication.setImageUrl(imageUrl);
            }

            publication = publicationRepository.save(publication);

            return ApiResponse.success(new PublicationResponseDTO(publication));

        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de la mise à jour: " + e.getMessage());
        }
    }

    // DELETE - Supprimer une publication
    @Transactional
    public ApiResponse<String> deletePublication(Long id) {
        try {
            Publication publication = publicationRepository.findById(id)
                    .orElse(null);

            if (publication == null) {
                return ApiResponse.error("Publication non trouvée");
            }

            // Supprimer l'image associée
            if (publication.getImageUrl() != null) {
                fileStorageService.deleteFile(publication.getImageUrl());
            }

            publicationRepository.delete(publication);

            return ApiResponse.success("Publication supprimée avec succès");

        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de la suppression: " + e.getMessage());
        }
    }

    // SEARCH - Recherche avancée
    public ApiResponse<Page<PublicationResponseDTO>> searchPublications(PublicationSearchDTO searchDTO) {
        try {
            Sort sort = searchDTO.getSortDirection().equalsIgnoreCase("ASC") ?
                    Sort.by(searchDTO.getSortBy()).ascending() :
                    Sort.by(searchDTO.getSortBy()).descending();

            Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize(), sort);

            Page<Publication> publications;

            // Filtrer par statut si spécifié
            if ("active".equals(searchDTO.getStatus())) {
                publications = publicationRepository.findActivePublications(LocalDateTime.now(), pageable);
            } else if ("expired".equals(searchDTO.getStatus())) {
                publications = publicationRepository.findExpiredPublications(LocalDateTime.now()).stream()
                        .collect(Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> publicationRepository.findAll(pageable)
                        ));
            } else if ("upcoming".equals(searchDTO.getStatus())) {
                publications = publicationRepository.findUpcomingPublications(LocalDateTime.now()).stream()
                        .collect(Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> publicationRepository.findAll(pageable)
                        ));
            } else {
                // Recherche générale avec filtres
                publications = publicationRepository.searchPublications(
                        searchDTO.getTitle(),
                        searchDTO.getEmail(),
                        searchDTO.getPlace(),
                        searchDTO.getStartDate(),
                        searchDTO.getEndDate(),
                        pageable
                );
            }

            Page<PublicationResponseDTO> response = publications.map(PublicationResponseDTO::new);

            return ApiResponse.success(response);
        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de la recherche: " + e.getMessage());
        }
    }

    // Obtenir les statistiques des publications
    public ApiResponse<PublicationStatsDTO> getPublicationStats(String email) {
        try {
            LocalDateTime now = LocalDateTime.now();

            PublicationStatsDTO stats = new PublicationStatsDTO();
            stats.setTotalPublications(publicationRepository.count());
            stats.setActivePublications(publicationRepository.countActivePublications(now));

            if (email != null) {
                stats.setPublicationsByEmail(publicationRepository.countByEmail(email));
            }

            // Calculer expired et upcoming
            stats.setExpiredPublications(publicationRepository.findExpiredPublications(now).size());
            stats.setUpcomingPublications(publicationRepository.findUpcomingPublications(now).size());

            return ApiResponse.success(stats);
        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de la récupération des statistiques: " + e.getMessage());
        }
    }

    // Vérifier si un utilisateur peut modifier/supprimer une publication
    public boolean canUserModifyPublication(Long publicationId, String userEmail) {
        try {
            Publication publication = publicationRepository.findById(publicationId).orElse(null);
            return publication != null && publication.getEmail().equals(userEmail);
        } catch (Exception e) {
            return false;
        }
    }
}
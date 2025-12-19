package com.example.stage.stage.service;

import com.example.stage.stage.dto.*;
import com.example.stage.stage.entity.*;
import com.example.stage.stage.repository.ProfessionalRepository;
import com.example.stage.stage.repository.CategoryRepository;
import com.example.stage.stage.repository.UserRepository;
import com.example.stage.stage.response.ApiResponse;
import com.example.stage.stage.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfessionalService {

    private final ProfessionalRepository professionalRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final JwtUtil jwtUtil;


    // CREATE - Inscription d'un nouveau professionnel
    @Transactional
    public ApiResponse<String> registerProfessional(ProfessionalCreateDTO request) {
        // Vérifier si l'email existe déjà
        if (userRepository.existsByEmail(request.getEmail())) {
            return ApiResponse.error("Un compte avec cet email existe déjà");
        }

        // Vérifier si les mots de passe correspondent
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return ApiResponse.error("Les mots de passe ne correspondent pas");
        }

        // Vérifier si la spécialité existe
        Category specialty = categoryRepository.findById(request.getSpecialtyId())
                .orElse(null);
        if (specialty == null) {
            return ApiResponse.error("Spécialité invalide");
        }

        try {
            // Créer le compte utilisateur
            User user = new User();
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(User.Role.PROFESSIONAL);
            user.setIsActive(false);
            user.setEmailVerified(false);
            user = userRepository.save(user);

            // Créer le profil professionnel
            Professional professional = new Professional();
            professional.setUser(user);
            professional.setFirstName(request.getFirstName());
            professional.setLastName(request.getLastName());
            professional.setPhone(request.getPhone());
            professional.setWhatsapp(request.getWhatsapp());
            professional.setDescription(request.getDescription());
            professional.setPosition(request.getPosition());
            professional.setServiceType(request.getServiceType());
            professional.setSpecialty(specialty);
            professional.setCertificationStatus(Professional.CertificationStatus.PENDING);

            professionalRepository.save(professional);
            emailService.sendVerificationEmail(user);

            return ApiResponse.success("Compte professionnel créé avec succès. Vérifiez votre email pour activer votre compte.");

        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de la création du compte: " + e.getMessage());
        }
    }

    // READ - Obtenir tous les professionnels avec pagination
    public ApiResponse<Page<ProfessionalResponseDTO>> getAllProfessionals(int page, int size, String sortBy, String sortDirection) {
        try {
            Sort sort = sortDirection.equalsIgnoreCase("ASC") ?
                    Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

            Pageable pageable = PageRequest.of(page, size, sort);
            Page<Professional> professionals = professionalRepository.findAll(pageable);

            Page<ProfessionalResponseDTO> response = professionals.map(ProfessionalResponseDTO::new);

            return ApiResponse.success(response);
        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de la récupération des professionnels: " + e.getMessage());
        }
    }

    // READ - Obtenir un professionnel par ID
    public ApiResponse<ProfessionalResponseDTO> getProfessionalById(Long id) {
        try {
            Professional professional = professionalRepository.findById(id)
                    .orElse(null);

            if (professional == null) {
                return ApiResponse.error("Professionnel non trouvé");
            }

            return ApiResponse.success(new ProfessionalResponseDTO(professional));
        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de la récupération du professionnel: " + e.getMessage());
        }
    }

    // READ - Obtenir un professionnel par email
    public ApiResponse<ProfessionalResponseDTO> getProfessionalByEmail(String email) {
        try {
            Professional professional = professionalRepository.findByUserEmail(email)
                    .orElse(null);

            if (professional == null) {
                return ApiResponse.error("Professionnel non trouvé");
            }

            return ApiResponse.success(new ProfessionalResponseDTO(professional));
        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de la récupération du professionnel: " + e.getMessage());
        }
    }

    // UPDATE - Mettre à jour un professionnel
    @Transactional
    public ApiResponse<ProfessionalResponseDTO> updateProfessional(Long id, ProfessionalUpdateDTO request) {
        try {
            Professional professional = professionalRepository.findById(id)
                    .orElse(null);

            if (professional == null) {
                return ApiResponse.error("Professionnel non trouvé");
            }

            // Vérifier si la spécialité existe
            Category specialty = categoryRepository.findById(request.getSpecialtyId())
                    .orElse(null);
            if (specialty == null) {
                return ApiResponse.error("Spécialité invalide");
            }

            // Mettre à jour les champs
            professional.setFirstName(request.getFirstName());
            professional.setLastName(request.getLastName());
            professional.setPhone(request.getPhone());
            professional.setWhatsapp(request.getWhatsapp());
            professional.setDescription(request.getDescription());
            professional.setPosition(request.getPosition());
            professional.setServiceType(request.getServiceType());
            professional.setSpecialty(specialty);

            professional = professionalRepository.save(professional);

            return ApiResponse.success(new ProfessionalResponseDTO(professional));
        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de la mise à jour: " + e.getMessage());
        }
    }

    // DELETE - Supprimer un professionnel
    @Transactional
    public ApiResponse<String> deleteProfessional(Long id) {
        try {
            Professional professional = professionalRepository.findById(id)
                    .orElse(null);

            if (professional == null) {
                return ApiResponse.error("Professionnel non trouvé");
            }

            // Supprimer le compte utilisateur associé
            User user = professional.getUser();
            professionalRepository.delete(professional);
            userRepository.delete(user);

            return ApiResponse.success("Professionnel supprimé avec succès");
        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de la suppression: " + e.getMessage());
        }
    }

    // SEARCH - Recherche avancée avec filtres
    public ApiResponse<Page<ProfessionalResponseDTO>> searchProfessionals(ProfessionalSearchDTO searchDTO) {
        try {
            Sort sort = searchDTO.getSortDirection().equalsIgnoreCase("ASC") ?
                    Sort.by(searchDTO.getSortBy()).ascending() :
                    Sort.by(searchDTO.getSortBy()).descending();

            Pageable pageable = PageRequest.of(searchDTO.getPage(), searchDTO.getSize(), sort);

            Professional.CertificationStatus certificationStatus = null;
            if (searchDTO.getCertificationStatus() != null && !searchDTO.getCertificationStatus().isEmpty()) {
                try {
                    certificationStatus = Professional.CertificationStatus.valueOf(searchDTO.getCertificationStatus());
                } catch (IllegalArgumentException e) {
                    return ApiResponse.error("Statut de certification invalide");
                }
            }

            Page<Professional> professionals = professionalRepository.searchProfessionals(
                    searchDTO.getFirstName(),
                    searchDTO.getLastName(),
                    searchDTO.getServiceType(),
                    searchDTO.getSpecialtyId(),
                    certificationStatus,
                    pageable
            );

            Page<ProfessionalResponseDTO> response = professionals.map(ProfessionalResponseDTO::new);

            return ApiResponse.success(response);
        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de la recherche: " + e.getMessage());
        }
    }

    // Mettre à jour le statut de certification
    @Transactional
    public ApiResponse<ProfessionalResponseDTO> updateCertificationStatus(Long id, CertificationStatusUpdateDTO request) {
        try {
            Professional professional = professionalRepository.findById(id)
                    .orElse(null);

            if (professional == null) {
                return ApiResponse.error("Professionnel non trouvé");
            }

            Professional.CertificationStatus newStatus;
            try {
                newStatus = Professional.CertificationStatus.valueOf(request.getCertificationStatus());
            } catch (IllegalArgumentException e) {
                return ApiResponse.error("Statut de certification invalide");
            }

            professional.setCertificationStatus(newStatus);
            professional = professionalRepository.save(professional);

            // Envoyer une notification par email

            return ApiResponse.success(new ProfessionalResponseDTO(professional));
        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de la mise à jour du statut: " + e.getMessage());
        }
    }

    public ApiResponse<ProfessionalResponseDTO> getProfessionalWithImages(Long id) {
        try {
            Professional professional = professionalRepository.findById(id)
                    .orElse(null);

            if (professional == null) {
                return ApiResponse.error("Professionnel non trouvé");
            }

            ProfessionalResponseDTO response = new ProfessionalResponseDTO(professional);

            // Les images sont déjà incluses dans le DTO grâce à la mise à jour
            return ApiResponse.success(response);
        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de la récupération: " + e.getMessage());
        }
    }

    // Récupérer un professionnel avec ses images par User ID
    public ApiResponse<ProfessionalResponseDTO> getProfessionalByUserIdWithImages(Long userId) {
        try {
            Professional professional = professionalRepository.findByUserId(userId)
                    .orElse(null);

            if (professional == null) {
                return ApiResponse.error("Professionnel non trouvé");
            }

            ProfessionalResponseDTO response = new ProfessionalResponseDTO(professional);

            return ApiResponse.success(response);
        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de la récupération: " + e.getMessage());
        }
    }



    // Obtenir les statistiques des professionnels
    public ApiResponse<ProfessionalStatsDTO> getProfessionalStats() {
        try {
            long totalProfessionals = professionalRepository.count();
            long pendingCertifications = professionalRepository.countByCertificationStatus(Professional.CertificationStatus.PENDING);
            long acceptedCertifications = professionalRepository.countByCertificationStatus(Professional.CertificationStatus.ACCEPTED);
            long rejectedCertifications = professionalRepository.countByCertificationStatus(Professional.CertificationStatus.REJECTED);
            long revokedCertifications = professionalRepository.countByCertificationStatus(Professional.CertificationStatus.REVOKED);

            ProfessionalStatsDTO stats = new ProfessionalStatsDTO();
            stats.setTotalProfessionals(totalProfessionals);
            stats.setPendingCertifications(pendingCertifications);
            stats.setAcceptedCertifications(acceptedCertifications);
            stats.setRejectedCertifications(rejectedCertifications);
            stats.setRevokedCertifications(revokedCertifications);

            return ApiResponse.success(stats);
        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de la récupération des statistiques: " + e.getMessage());
        }
    }

    // Vérifier si un professionnel est propriétaire d'un compte
    public boolean isProfessionalOwner(Long professionalId, String email) {
        try {
            Professional professional = professionalRepository.findById(professionalId).orElse(null);
            return professional != null && professional.getUser().getEmail().equals(email);
        } catch (Exception e) {
            return false;
        }
    }




    /**
     * Méthode 1: Récupérer le profil complet d'un professionnel via son token
     */
    @Transactional(readOnly = true)
    public ApiResponse<ProfessionalDetailDTO> getProfessionalProfile(String token) {
        try {
            // Valider et extraire l'email du token
            if (!jwtUtil.validateToken(token)) {
                return ApiResponse.error("Token invalide ou expiré");
            }

            String email = jwtUtil.extractUsername(token);

            // Récupérer l'utilisateur
            Optional<User> userOpt = userRepository.findByEmail(email);
            if (userOpt.isEmpty()) {
                return ApiResponse.error("Utilisateur non trouvé");
            }

            User user = userOpt.get();

            // Vérifier que c'est bien un professionnel
            if (user.getRole() != User.Role.PROFESSIONAL) {
                return ApiResponse.error("L'utilisateur n'est pas un professionnel");
            }

            // Récupérer les informations du professionnel
            Optional<Professional> professionalOpt = professionalRepository.findByUser(user);
            if (professionalOpt.isEmpty()) {
                return ApiResponse.error("Profil professionnel non trouvé");
            }

            Professional professional = professionalOpt.get();

            // Construire le DTO avec tous les détails
            ProfessionalDetailDTO dto = buildProfessionalDetailDTO(professional);

            return ApiResponse.success("Profil professionnel récupéré avec succès", dto);

        } catch (Exception e) {
            return ApiResponse.error("Erreur interne du serveur");
        }
    }

    @Transactional(readOnly = true)
    public ApiResponse<List<ProfessionalSummaryDTO>> getProfessionalsByCategory(Long categoryId) {
        try {

            // Vérifier si la catégorie existe
            Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
            if (categoryOpt.isEmpty()) {
                return ApiResponse.error("Catégorie non trouvée avec l'ID: " + categoryId);
            }

            Category category = categoryOpt.get();

            // Vérifier si la catégorie est active
            if (!category.getIsActive()) {
                return ApiResponse.error("Cette catégorie n'est pas active");
            }

            // Récupérer les professionnels ayant cette spécialité et qui sont actifs
            List<Professional> professionals = professionalRepository.findBySpecialtyIdAndUser_IsActiveTrue(categoryId);

            if (professionals.isEmpty()) {
                String message = String.format("Aucun professionnel trouvé pour la catégorie '%s'", category.getName());
                return ApiResponse.success(message, new ArrayList<>());
            }

            // Convertir en DTOs avec calcul des ratings
            List<ProfessionalSummaryDTO> professionalDTOs = professionals.stream()
                    .map(this::buildProfessionalSummaryDTO)
                    .collect(Collectors.toList());

            // Trier selon la logique standard:
            // 1. Professionnels certifiés en premier, ordonnés par rating décroissant
            // 2. Professionnels non certifiés après, ordonnés par rating décroissant
            professionalDTOs.sort((p1, p2) -> {
                // D'abord trier par statut de certification
                boolean p1Certified = p1.getCertificationStatus() == Professional.CertificationStatus.ACCEPTED;
                boolean p2Certified = p2.getCertificationStatus() == Professional.CertificationStatus.ACCEPTED;

                if (p1Certified && !p2Certified) {
                    return -1; // p1 certifié va avant p2 non certifié
                }
                if (!p1Certified && p2Certified) {
                    return 1; // p2 certifié va avant p1 non certifié
                }

                // Si même statut de certification, trier par rating décroissant
                Double rating1 = p1.getRatingAverage() != null ? p1.getRatingAverage() : 0.0;
                Double rating2 = p2.getRatingAverage() != null ? p2.getRatingAverage() : 0.0;

                int ratingComparison = Double.compare(rating2, rating1); // Ordre décroissant

                // Si même rating, trier par nombre d'avis décroissant
                if (ratingComparison == 0) {
                    Integer count1 = p1.getRatingCount() != null ? p1.getRatingCount() : 0;
                    Integer count2 = p2.getRatingCount() != null ? p2.getRatingCount() : 0;
                    return Integer.compare(count2, count1);
                }

                return ratingComparison;
            });

            String successMessage = String.format("Professionnels pour la catégorie '%s' récupérés avec succès", category.getName());

            return ApiResponse.success(successMessage, professionalDTOs);

        } catch (Exception e) {
            return ApiResponse.error("Erreur interne du serveur");
        }
    }

    private CategoryDTO convertCategoryToDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setIcon(category.getIcon());
        dto.setImage(category.getImage());
        dto.setIsActive(category.getIsActive());
        dto.setDisplayOrder(category.getDisplayOrder());

        // Informations sur la catégorie parent
        if (category.getParentCategory() != null) {
            dto.setParentCategoryId(category.getParentCategory().getId());
            dto.setParentCategoryName(category.getParentCategory().getName());
        }

        // Sous-catégories
        if (category.getSubCategories() != null && !category.getSubCategories().isEmpty()) {
            List<CategoryDTO> subCategoryDTOs = category.getSubCategories().stream()
                    .filter(subCat -> subCat.getIsActive())
                    .map(this::convertCategoryToDTO)
                    .collect(Collectors.toList());
            dto.setSubCategories(subCategoryDTOs);
        }

        // Compter les professionnels actifs dans cette catégorie
        long professionalCount = professionalRepository.countBySpecialtyIdAndUser_IsActiveTrue(category.getId());
        dto.setProfessionalCount(professionalCount);

        return dto;
    }


    /**
     * API UNIFIÉE: Récupérer les professionnels avec filtrage par catégorie et/ou serviceType
     * Inspirée de getAllProfessionalsOrderedByServiceType
     */
    @Transactional(readOnly = true)
    public ApiResponse<List<ProfessionalSummaryDTO>> getProfessionalsFiltered(Long categoryId, String serviceType) {
        try {

            List<Professional> allProfessionals;

            // ÉTAPE 1: Filtrage par catégorie (si spécifiée)
            if (categoryId != null) {
                // Vérifier si la catégorie existe
                Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
                if (categoryOpt.isEmpty()) {
                    return ApiResponse.error("Catégorie non trouvée avec l'ID: " + categoryId);
                }

                Category category = categoryOpt.get();

                // Vérifier si la catégorie est active
                if (!category.getIsActive()) {
                    return ApiResponse.error("Cette catégorie n'est pas active");
                }

                // Récupérer les professionnels de cette catégorie
                allProfessionals = professionalRepository.findBySpecialtyIdAndUser_IsActiveTrue(categoryId);

            } else {
                // Si pas de catégorie spécifiée, récupérer tous les professionnels actifs
                allProfessionals = professionalRepository.findByUser_IsActiveTrue();

            }

            if (allProfessionals.isEmpty()) {
                String message = categoryId != null ?
                        "Aucun professionnel trouvé pour cette catégorie" :
                        "Aucun professionnel trouvé";
                return ApiResponse.success(message, new ArrayList<>());
            }

            // ÉTAPE 2: Filtrage par serviceType (si spécifié)
            if (serviceType != null && !serviceType.trim().isEmpty()) {
                String normalizedServiceType = serviceType.trim();
                allProfessionals = allProfessionals.stream()
                        .filter(professional -> professional.getServiceType() != null &&
                                professional.getServiceType().toLowerCase().contains(normalizedServiceType.toLowerCase()))
                        .collect(Collectors.toList());


            }

            if (allProfessionals.isEmpty()) {
                String message = buildNoResultMessage(categoryId, serviceType);
                return ApiResponse.success(message, new ArrayList<>());
            }

            // ÉTAPE 3: Convertir en DTOs avec calcul des ratings
            List<ProfessionalSummaryDTO> professionalDTOs = allProfessionals.stream()
                    .map(this::buildProfessionalSummaryDTO)
                    .collect(Collectors.toList());

            // ÉTAPE 4: Trier selon la logique standard
            // 1. Professionnels certifiés en premier, ordonnés par rating décroissant
            // 2. Professionnels non certifiés après, ordonnés par rating décroissant
            professionalDTOs.sort((p1, p2) -> {
                // D'abord trier par statut de certification
                boolean p1Certified = p1.getCertificationStatus() == Professional.CertificationStatus.ACCEPTED;
                boolean p2Certified = p2.getCertificationStatus() == Professional.CertificationStatus.ACCEPTED;

                if (p1Certified && !p2Certified) {
                    return -1; // p1 certifié va avant p2 non certifié
                }
                if (!p1Certified && p2Certified) {
                    return 1; // p2 certifié va avant p1 non certifié
                }

                // Si même statut de certification, trier par rating décroissant
                Double rating1 = p1.getRatingAverage() != null ? p1.getRatingAverage() : 0.0;
                Double rating2 = p2.getRatingAverage() != null ? p2.getRatingAverage() : 0.0;

                int ratingComparison = Double.compare(rating2, rating1); // Ordre décroissant

                // Si même rating, trier par nombre d'avis décroissant
                if (ratingComparison == 0) {
                    Integer count1 = p1.getRatingCount() != null ? p1.getRatingCount() : 0;
                    Integer count2 = p2.getRatingCount() != null ? p2.getRatingCount() : 0;
                    return Integer.compare(count2, count1);
                }

                return ratingComparison;
            });

            // ÉTAPE 5: Message de succès personnalisé
            String successMessage = buildSuccessMessage(categoryId, serviceType);

            return ApiResponse.success(successMessage, professionalDTOs);

        } catch (Exception e) {
            return ApiResponse.error("Erreur interne du serveur");
        }
    }

    /**
     * Construire le message de succès selon les filtres appliqués
     */
    private String buildSuccessMessage(Long categoryId, String serviceType) {
        if (categoryId != null && serviceType != null && !serviceType.trim().isEmpty()) {
            // Les deux filtres sont appliqués
            Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
            String categoryName = categoryOpt.isPresent() ? categoryOpt.get().getName() : "Catégorie";
            return String.format("Professionnels pour la catégorie '%s' avec le service '%s' récupérés avec succès",
                    categoryName, serviceType);
        } else if (categoryId != null) {
            // Seul le filtre catégorie est appliqué
            Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
            String categoryName = categoryOpt.isPresent() ? categoryOpt.get().getName() : "Catégorie";
            return String.format("Professionnels pour la catégorie '%s' récupérés avec succès", categoryName);
        } else if (serviceType != null && !serviceType.trim().isEmpty()) {
            // Seul le filtre serviceType est appliqué
            return String.format("Professionnels pour le service '%s' récupérés avec succès", serviceType);
        } else {
            // Aucun filtre appliqué
            return "Professionnels récupérés avec succès";
        }
    }

    /**
     * Construire le message quand aucun résultat n'est trouvé
     */
    private String buildNoResultMessage(Long categoryId, String serviceType) {
        if (categoryId != null && serviceType != null && !serviceType.trim().isEmpty()) {
            // Les deux filtres sont appliqués
            Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
            String categoryName = categoryOpt.isPresent() ? categoryOpt.get().getName() : "cette catégorie";
            return String.format("Aucun professionnel trouvé pour la catégorie '%s' avec le service '%s'",
                    categoryName, serviceType);
        } else if (categoryId != null) {
            // Seul le filtre catégorie est appliqué
            Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
            String categoryName = categoryOpt.isPresent() ? categoryOpt.get().getName() : "cette catégorie";
            return String.format("Aucun professionnel trouvé pour la catégorie '%s'", categoryName);
        } else if (serviceType != null && !serviceType.trim().isEmpty()) {
            // Seul le filtre serviceType est appliqué
            return String.format("Aucun professionnel trouvé pour le service '%s'", serviceType);
        } else {
            // Aucun filtre appliqué
            return "Aucun professionnel trouvé";
        }
    }

    /**
     * Méthode 2: Récupérer tous les professionnels ordonnés par certification et rating
     */
    @Transactional(readOnly = true)
    public ApiResponse<List<ProfessionalSummaryDTO>> getAllProfessionalsOrderedByServiceType(String serviceType) {
        try {


            // Récupérer tous les professionnels actifs
            List<Professional> allProfessionals = professionalRepository.findByUser_IsActiveTrue();

            if (allProfessionals.isEmpty()) {
                return ApiResponse.success("Aucun professionnel trouvé", new ArrayList<>());
            }

            // Filtrer par serviceType si spécifié
            if (serviceType != null && !serviceType.trim().isEmpty()) {
                String normalizedServiceType = serviceType.trim();
                allProfessionals = allProfessionals.stream()
                        .filter(professional -> professional.getServiceType() != null &&
                                professional.getServiceType().toLowerCase().contains(normalizedServiceType.toLowerCase()))
                        .collect(Collectors.toList());


            }

            if (allProfessionals.isEmpty()) {
                String message = serviceType != null ?
                        "Aucun professionnel trouvé pour le service: " + serviceType :
                        "Aucun professionnel trouvé";
                return ApiResponse.success(message, new ArrayList<>());
            }

            // Convertir en DTOs avec calcul des ratings
            List<ProfessionalSummaryDTO> professionalDTOs = allProfessionals.stream()
                    .map(this::buildProfessionalSummaryDTO)
                    .collect(Collectors.toList());

            // Trier selon la logique demandée:
            // 1. Professionnels certifiés en premier, ordonnés par rating décroissant
            // 2. Professionnels non certifiés après, ordonnés par rating décroissant
            professionalDTOs.sort((p1, p2) -> {
                // D'abord trier par statut de certification
                boolean p1Certified = p1.getCertificationStatus() == Professional.CertificationStatus.ACCEPTED;
                boolean p2Certified = p2.getCertificationStatus() == Professional.CertificationStatus.ACCEPTED;

                if (p1Certified && !p2Certified) {
                    return -1; // p1 certifié va avant p2 non certifié
                }
                if (!p1Certified && p2Certified) {
                    return 1; // p2 certifié va avant p1 non certifié
                }

                // Si même statut de certification, trier par rating décroissant
                Double rating1 = p1.getRatingAverage() != null ? p1.getRatingAverage() : 0.0;
                Double rating2 = p2.getRatingAverage() != null ? p2.getRatingAverage() : 0.0;

                int ratingComparison = Double.compare(rating2, rating1); // Ordre décroissant

                // Si même rating, trier par nombre d'avis décroissant
                if (ratingComparison == 0) {
                    Integer count1 = p1.getRatingCount() != null ? p1.getRatingCount() : 0;
                    Integer count2 = p2.getRatingCount() != null ? p2.getRatingCount() : 0;
                    return Integer.compare(count2, count1);
                }

                return ratingComparison;
            });

            String successMessage = serviceType != null ?
                    String.format("Professionnels pour le service '%s' récupérés avec succès", serviceType) :
                    "Professionnels récupérés avec succès";

            return ApiResponse.success(successMessage, professionalDTOs);

        } catch (Exception e) {
            return ApiResponse.error("Erreur interne du serveur");
        }
    }

    /**
     * Construire le DTO détaillé d'un professionnel (pour la méthode 1)
     */
    private ProfessionalDetailDTO buildProfessionalDetailDTO(Professional professional) {
        ProfessionalDetailDTO dto = new ProfessionalDetailDTO();

        // Informations utilisateur
        User user = professional.getUser();
        dto.setUserId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setIsActive(user.getIsActive());
        dto.setEmailVerified(user.getEmailVerified());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setLastLogin(user.getLastLogin());

        // Informations professionnel
        dto.setProfessionalId(professional.getId());
        dto.setFirstName(professional.getFirstName());
        dto.setLastName(professional.getLastName());
        dto.setPhone(professional.getPhone());
        dto.setWhatsapp(professional.getWhatsapp());
        dto.setProfilePhotoUrl(professional.getProfilePhotoUrl());
        dto.setDescription(professional.getDescription());
        dto.setPosition(professional.getPosition());
        dto.setServiceType(professional.getServiceType());
        dto.setCertificationStatus(professional.getCertificationStatus());
        dto.setProfessionalCreatedAt(professional.getCreatedAt());
        dto.setProfessionalUpdatedAt(professional.getUpdatedAt());

        // Spécialité
        if (professional.getSpecialty() != null) {
            CategoryDTO specialtyDTO = new CategoryDTO();
            specialtyDTO.setId(professional.getSpecialty().getId());
            specialtyDTO.setName(professional.getSpecialty().getName());
            specialtyDTO.setIcon(professional.getSpecialty().getIcon());
            specialtyDTO.setImage(professional.getSpecialty().getImage());
            dto.setSpecialty(specialtyDTO);
        }

        // Calculer les statistiques des avis
        calculateRatingStats(professional, dto);

        // Avis récents (limiter à 10)
        List<ReviewDTO> recentReviews = professional.getReviews() != null ?
                professional.getReviews().stream()
                        .sorted((r1, r2) -> r2.getCreatedAt().compareTo(r1.getCreatedAt()))
                        .limit(10)
                        .map(this::convertReviewToDTO)
                        .collect(Collectors.toList()) : new ArrayList<>();
        dto.setRecentReviews(recentReviews);

        // Informations de certification si acceptée
        if (professional.getCertificationStatus() == Professional.CertificationStatus.ACCEPTED) {
            CertificationRequest certRequest = professional.getCertificationRequest();
            if (certRequest != null) {
                CertificationInfoDTO certInfo = new CertificationInfoDTO();
                certInfo.setStatus(certRequest.getStatus());
                certInfo.setSubmittedAt(certRequest.getSubmittedAt());
                certInfo.setProcessedAt(certRequest.getProcessedAt());
                certInfo.setProfilePhotoUrl(certRequest.getProfilePhotoUrl());
                certInfo.setIdentityCardUrl(certRequest.getIdentityCardUrl());
                certInfo.setSpecializationDocumentUrl(certRequest.getSpecializationDocumentUrl());
                dto.setCertificationInfo(certInfo);
            }
        }

        return dto;
    }

    /**
     * Construire le DTO résumé d'un professionnel (pour la méthode 2)
     */
    private ProfessionalSummaryDTO buildProfessionalSummaryDTO(Professional professional) {
        ProfessionalSummaryDTO dto = new ProfessionalSummaryDTO();

        // Informations de base
        dto.setProfessionalId(professional.getId());
        dto.setEmail(professional.getUser().getEmail());
        dto.setFirstName(professional.getFirstName());
        dto.setLastName(professional.getLastName());
        dto.setPhone(professional.getPhone());
        dto.setWhatsapp(professional.getWhatsapp());
        dto.setProfilePhotoUrl(professional.getProfilePhotoUrl());
        dto.setDescription(professional.getDescription());
        dto.setPosition(professional.getPosition());
        dto.setServiceType(professional.getServiceType());
        dto.setCertificationStatus(professional.getCertificationStatus());
        dto.setCreatedAt(professional.getCreatedAt());

        // Spécialité
        if (professional.getSpecialty() != null) {
            dto.setSpecialtyName(professional.getSpecialty().getName());
            dto.setSpecialtyIcon(professional.getSpecialty().getIcon());
        }

        // Calculer les statistiques des avis
        calculateRatingStats(professional, dto);

        // Indicateur de certification
        dto.setIsCertified(professional.getCertificationStatus() == Professional.CertificationStatus.ACCEPTED);

        return dto;
    }

    /**
     * Calculer les statistiques des avis
     */
    private void calculateRatingStats(Professional professional, Object dto) {
        List<Review> reviews = professional.getReviews();
        if (reviews != null && !reviews.isEmpty()) {
            double average = reviews.stream()
                    .mapToInt(Review::getRating)
                    .average()
                    .orElse(0.0);

            if (dto instanceof ProfessionalDetailDTO) {
                ((ProfessionalDetailDTO) dto).setRatingAverage(Math.round(average * 10.0) / 10.0);
                ((ProfessionalDetailDTO) dto).setRatingCount(reviews.size());
            } else if (dto instanceof ProfessionalSummaryDTO) {
                ((ProfessionalSummaryDTO) dto).setRatingAverage(Math.round(average * 10.0) / 10.0);
                ((ProfessionalSummaryDTO) dto).setRatingCount(reviews.size());
            }
        } else {
            if (dto instanceof ProfessionalDetailDTO) {
                ((ProfessionalDetailDTO) dto).setRatingAverage(0.0);
                ((ProfessionalDetailDTO) dto).setRatingCount(0);
            } else if (dto instanceof ProfessionalSummaryDTO) {
                ((ProfessionalSummaryDTO) dto).setRatingAverage(0.0);
                ((ProfessionalSummaryDTO) dto).setRatingCount(0);
            }
        }
    }

    /**
     * Convertir Review en ReviewDTO
     */
    private ReviewDTO convertReviewToDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setProfessionalId(review.getProfessional().getId());
        dto.setClientName(review.getClientName()); // Ou nom complet si disponible
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setCreatedAt(review.getCreatedAt());
        return dto;
    }
}

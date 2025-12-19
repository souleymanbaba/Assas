package com.example.stage.stage.service;

import com.example.stage.stage.dto.*;
import com.example.stage.stage.entity.CertificationRequest;
import com.example.stage.stage.entity.Professional;
import com.example.stage.stage.entity.User;
import com.example.stage.stage.repository.CertificationRequestRepository;
import com.example.stage.stage.repository.ProfessionalRepository;
import com.example.stage.stage.repository.UserRepository;
import com.example.stage.stage.response.ApiResponse;
import com.example.stage.stage.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CertificationService {

    private final CertificationRequestRepository certificationRequestRepository;
    private final ProfessionalRepository professionalRepository;
    private final FileStorageService fileStorageService;
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    // API 1: Soumettre une demande de certification
    @Transactional
    public ApiResponse<String> submitCertificationRequest(String token, CertificationSubmissionDTO request) {
        try {

            // 1. Valider et extraire l'email du token
            if (!jwtUtil.validateToken(token)) {
                return ApiResponse.error("Token invalide ou expiré");
            }

            String email = jwtUtil.extractUsername(token);

            // 2. Récupérer l'utilisateur
            Optional<User> userOpt = userRepository.findByEmail(email);
            if (userOpt.isEmpty()) {
                return ApiResponse.error("Utilisateur non trouvé");
            }

            User user = userOpt.get();

            // 3. Vérifier que c'est bien un professionnel
            if (user.getRole() != User.Role.PROFESSIONAL) {
                return ApiResponse.error("Seuls les professionnels peuvent soumettre une demande de certification");
            }

            // 4. Récupérer les informations du professionnel
            Optional<Professional> professionalOpt = professionalRepository.findByUser(user);
            if (professionalOpt.isEmpty()) {
                return ApiResponse.error("Profil professionnel non trouvé");
            }

            Professional professional = professionalOpt.get();

            // 5. Vérifier si une demande existe déjà
            if (certificationRequestRepository.existsByProfessionalId(professional.getId())) {
                return ApiResponse.error("Une demande de certification existe déjà pour ce professionnel");
            }

            // 6. Vérifier que les fichiers requis sont présents
            if (request.getProfilePhoto() == null || request.getProfilePhoto().isEmpty()) {
                return ApiResponse.error("La photo de profil est requise");
            }
            if (request.getIdentityCard() == null || request.getIdentityCard().isEmpty()) {
                return ApiResponse.error("La carte d'identité est requise");
            }
            if (request.getSpecializationDocument() == null || request.getSpecializationDocument().isEmpty()) {
                return ApiResponse.error("Le document de spécialisation est requis");
            }

            // 7. Sauvegarder les fichiers
            String profilePhotoUrl = fileStorageService.storeFile(request.getProfilePhoto(), "profiles");
            String identityCardUrl = fileStorageService.storeFile(request.getIdentityCard(), "identity-cards");
            String specializationDocumentUrl = fileStorageService.storeFile(request.getSpecializationDocument(), "specializations");

            // 8. Créer la demande de certification
            CertificationRequest certificationRequest = new CertificationRequest();
            certificationRequest.setProfessional(professional);
            certificationRequest.setProfilePhotoUrl(profilePhotoUrl);
            certificationRequest.setIdentityCardUrl(identityCardUrl);
            certificationRequest.setSpecializationDocumentUrl(specializationDocumentUrl);
            certificationRequest.setStatus(CertificationRequest.CertificationStatus.PENDING);

            certificationRequestRepository.save(certificationRequest);

            // 9. Mettre à jour la photo de profil du professionnel
            professional.setProfilePhotoUrl(profilePhotoUrl);
            professional.setCertificationStatus(Professional.CertificationStatus.PENDING);
            professionalRepository.save(professional);

            // 10. Envoyer une notification email
            try {
                // emailService.sendCertificationSubmissionEmail(professional.getUser());
            } catch (Exception emailException) {
                // Ne pas faire échouer la demande si l'email échoue
            }

            return ApiResponse.success("Demande de certification soumise avec succès. Vous recevrez une notification par email une fois la demande traitée.");

        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de la soumission: " + e.getMessage());
        }
    }


    // API 2: Accepter une demande de certification
    @Transactional
    public ApiResponse<CertificationResponseDTO> acceptCertification(Long requestId, CertificationDecisionDTO decision, Long adminId) {
        try {
            CertificationRequest request = certificationRequestRepository.findById(requestId)
                    .orElse(null);
            if (request == null) {
                return ApiResponse.error("Demande de certification non trouvée");
            }

            if (request.getStatus() != CertificationRequest.CertificationStatus.PENDING) {
                return ApiResponse.error("Cette demande a déjà été traitée");
            }

            // Mettre à jour la demande
            request.setStatus(CertificationRequest.CertificationStatus.ACCEPTED);
            request.setProcessedAt(LocalDateTime.now());
            request.setProcessedBy(adminId);
            request.setAdminNotes(decision.getAdminNotes());

            // Mettre à jour le statut du professionnel
            Professional professional = request.getProfessional();
            professional.setCertificationStatus(Professional.CertificationStatus.ACCEPTED);

            certificationRequestRepository.save(request);
            professionalRepository.save(professional);

            // Envoyer une notification email
           emailService.sendCertificationApprovalEmail(professional.getUser(), decision.getReason());

            return ApiResponse.success(new CertificationResponseDTO(request));

        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de l'acceptation: " + e.getMessage());
        }
    }

    // API 3: Rejeter une demande de certification
    @Transactional
    public ApiResponse<CertificationResponseDTO> rejectCertification(Long requestId, CertificationDecisionDTO decision, Long adminId) {
        try {
            CertificationRequest request = certificationRequestRepository.findById(requestId)
                    .orElse(null);
            if (request == null) {
                return ApiResponse.error("Demande de certification non trouvée");
            }

            if (request.getStatus() != CertificationRequest.CertificationStatus.PENDING) {
                return ApiResponse.error("Cette demande a déjà été traitée");
            }

            // Mettre à jour la demande
            request.setStatus(CertificationRequest.CertificationStatus.REJECTED);
            request.setProcessedAt(LocalDateTime.now());
            request.setProcessedBy(adminId);
            request.setRejectionReason(decision.getReason());
            request.setAdminNotes(decision.getAdminNotes());

            // Mettre à jour le statut du professionnel
            Professional professional = request.getProfessional();
            professional.setCertificationStatus(Professional.CertificationStatus.REJECTED);

            certificationRequestRepository.save(request);
            professionalRepository.save(professional);

            // Envoyer une notification email
//            emailService.sendCertificationRejectedEmail(professional.getUser(), decision.getReason());

            return ApiResponse.success(new CertificationResponseDTO(request));

        } catch (Exception e) {
            return ApiResponse.error("Erreur lors du rejet: " + e.getMessage());
        }
    }

    // API 4: Révoquer une certification
    @Transactional
    public ApiResponse<CertificationResponseDTO> revokeCertification(Long requestId, CertificationRevocationDTO revocation, Long adminId) {
        try {
            CertificationRequest request = certificationRequestRepository.findById(requestId)
                    .orElse(null);
            if (request == null) {
                return ApiResponse.error("Demande de certification non trouvée");
            }

            if (request.getStatus() != CertificationRequest.CertificationStatus.ACCEPTED) {
                return ApiResponse.error("Seules les certifications acceptées peuvent être révoquées");
            }

            // Mettre à jour la demande
            request.setStatus(CertificationRequest.CertificationStatus.REVOKED);
            request.setProcessedAt(LocalDateTime.now());
            request.setProcessedBy(adminId);
            request.setRejectionReason(revocation.getReason());
            request.setAdminNotes(revocation.getAdminNotes());

            // Mettre à jour le statut du professionnel
            Professional professional = request.getProfessional();
            professional.setCertificationStatus(Professional.CertificationStatus.REVOKED);

            certificationRequestRepository.save(request);
            professionalRepository.save(professional);

            // Envoyer une notification email
//            emailService.sendCertificationRevokedEmail(professional.getUser(), revocation.getReason());

            return ApiResponse.success(new CertificationResponseDTO(request));

        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de la révocation: " + e.getMessage());
        }
    }

    // Obtenir toutes les demandes de certification avec pagination
    public ApiResponse<Page<CertificationResponseDTO>> getAllCertificationRequests(int page, int size, String sortBy, String sortDirection) {
        try {
            Sort sort = sortDirection.equalsIgnoreCase("ASC") ?
                    Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

            Pageable pageable = PageRequest.of(page, size, sort);
            Page<CertificationRequest> requests = certificationRequestRepository.findAll(pageable);

            Page<CertificationResponseDTO> response = requests.map(CertificationResponseDTO::new);

            return ApiResponse.success(response);
        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de la récupération: " + e.getMessage());
        }
    }

    // Obtenir les demandes par statut
    public ApiResponse<Page<CertificationResponseDTO>> getCertificationRequestsByStatus(
            CertificationRequest.CertificationStatus status, int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("submittedAt").descending());
            Page<CertificationRequest> requests = certificationRequestRepository.findByStatus(status, pageable);

            Page<CertificationResponseDTO> response = requests.map(CertificationResponseDTO::new);

            return ApiResponse.success(response);
        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de la récupération: " + e.getMessage());
        }
    }

    // Obtenir une demande par ID professionnel
    public ApiResponse<CertificationResponseDTO> getCertificationRequestByProfessionalId(Long professionalId) {
        try {
            CertificationRequest request = certificationRequestRepository.findByProfessionalId(professionalId)
                    .orElse(null);

            if (request == null) {
                return ApiResponse.error("Aucune demande de certification trouvée pour ce professionnel");
            }

            return ApiResponse.success(new CertificationResponseDTO(request));
        } catch (Exception e) {
            return ApiResponse.error("Erreur lors de la récupération: " + e.getMessage());
        }
    }
}
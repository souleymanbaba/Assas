package com.example.stage.stage.service;

import com.example.stage.stage.dto.*;
import com.example.stage.stage.entity.Category;
import com.example.stage.stage.entity.Professional;
import com.example.stage.stage.entity.User;
import com.example.stage.stage.repository.CategoryRepository;
import com.example.stage.stage.repository.ProfessionalRepository;
import com.example.stage.stage.repository.UserRepository;
import com.example.stage.stage.response.ApiResponse;
import com.example.stage.stage.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j // Ajouter pour les logs
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final ProfessionalRepository professionalRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;
    private final CategoryRepository categoryRepository;

    @Transactional
    public ApiResponse<String> registerClient(ClientRegistrationDTO request) {
        // Vérifier si l'email existe déjà
        if (userRepository.existsByEmail(request.getEmail())) {
            return ApiResponse.error("Un compte avec cet email existe déjà");
        }

        // Créer le compte utilisateur
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(User.Role.CLIENT);
        user.setIsActive(true);
        user.setEmailVerified(false);

        userRepository.save(user);

        // Envoyer email de vérification
        emailService.sendVerificationEmail(user);

        return ApiResponse.success("Compte client créé avec succès. Vérifiez votre email pour activer votre compte.");
    }

    // ✅ VERSION CORRIGÉE - Avec @Transactional et sans try-catch problématique
    @Transactional
    public ApiResponse<String> registerProfessional(ProfessionalCreateDTO request) {

        log.info("Début inscription professionnel: {}", request.getEmail());

        // 1. Vérifications préalables (avant modifications DB)
        if (userRepository.existsByEmail(request.getEmail())) {
            log.warn("Tentative d'inscription avec email existant: {}", request.getEmail());
            return ApiResponse.error("Un compte avec cet email existe déjà");
        }

        // Vérifier si les mots de passe correspondent
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            log.warn("Mots de passe non correspondants pour: {}", request.getEmail());
            return ApiResponse.error("Les mots de passe ne correspondent pas");
        }

        // Vérifier si la spécialité existe
        Category specialty = categoryRepository.findById(request.getSpecialtyId())
                .orElse(null);
        if (specialty == null) {
            log.warn("Spécialité invalide ID: {}", request.getSpecialtyId());
            return ApiResponse.error("Spécialité invalide");
        }

        // 2. Création des entités (SANS try-catch global)
        // Laisser les exceptions DataIntegrityViolationException remonter naturellement
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(User.Role.PROFESSIONAL);
        user.setIsActive(false);
        user.setEmailVerified(false);
        user = userRepository.save(user);

        log.info("Utilisateur créé avec ID: {}", user.getId());

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

        professional = professionalRepository.save(professional);
        log.info("Professionnel créé avec ID: {}", professional.getId());

        // 3. Envoi email (peut échouer sans affecter la transaction principale)
        try {
            emailService.sendVerificationEmail(user);
            log.info("Email de vérification envoyé à: {}", user.getEmail());
        } catch (Exception emailException) {
            log.warn("Erreur envoi email pour {}: {}", user.getEmail(), emailException.getMessage());
            // Ne pas faire échouer l'inscription si l'email échoue
        }

        return ApiResponse.success("Compte professionnel créé avec succès. Vérifiez votre email pour activer votre compte.");
    }

    @Transactional
    public ApiResponse<String> registeradmin(AdminCreateDTO request) {

        log.info("Début inscription professionnel: {}", request.getEmail());

        // 1. Vérifications préalables (avant modifications DB)
        if (userRepository.existsByEmail(request.getEmail())) {
            log.warn("Tentative d'inscription avec email existant: {}", request.getEmail());
            return ApiResponse.error("Un compte avec cet email existe déjà");
        }

        // Vérifier si les mots de passe correspondent
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            log.warn("Mots de passe non correspondants pour: {}", request.getEmail());
            return ApiResponse.error("Les mots de passe ne correspondent pas");
        }

        // Vérifier si la spécialité existe


        // 2. Création des entités (SANS try-catch global)
        // Laisser les exceptions DataIntegrityViolationException remonter naturellement
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(User.Role.ADMIN);
        user.setIsActive(true);
        user.setEmailVerified(true);
        user = userRepository.save(user);

        log.info("Utilisateur créé avec ID: {}", user.getId());





        // 3. Envoi email (peut échouer sans affecter la transaction principale)
        try {
//            emailService.sendVerificationEmail(user);
            log.info("Email de vérification envoyé à: {}", user.getEmail());
        } catch (Exception emailException) {
            log.warn("Erreur envoi email pour {}: {}", user.getEmail(), emailException.getMessage());
            // Ne pas faire échouer l'inscription si l'email échoue
        }

        return ApiResponse.success("Compte Admin créé avec succès. Vérifiez votre email pour activer votre compte.");
    }

    public ApiResponse<AuthResponseDTO> login(LoginDTO request) {
        try {
            // Authentifier l'utilisateur
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            User user = (User) authentication.getPrincipal();

            log.info("Connexion réussie pour: {}", user.getEmail());

            // ✅ Décommenter ces vérifications si nécessaire
            // Vérifier si le compte est activé
            // if (!user.getEmailVerified()) {
            //     return ApiResponse.error("Veuillez vérifier votre email avant de vous connecter");
            // }
            //
            // if (!user.getIsActive()) {
            //     return ApiResponse.error("Votre compte a été désactivé");
            // }

            // Générer les tokens
            String accessToken = jwtUtil.generateToken(user);
            String refreshToken = jwtUtil.generateRefreshToken(user);

            // Mettre à jour la dernière connexion
            user.setLastLogin(LocalDateTime.now());
            userRepository.save(user);

            AuthResponseDTO response = new AuthResponseDTO();
            response.setAccessToken(accessToken);
            response.setRefreshToken(refreshToken);
            response.setTokenType("Bearer");
            response.setUser(convertToUserDTO(user));

            return ApiResponse.success("Connexion réussie", response);

        } catch (Exception e) {
            log.error("Erreur de connexion pour {}: {}", request.getEmail(), e.getMessage());
            return ApiResponse.error("Email ou mot de passe incorrect");
        }
    }

    public ApiResponse<AuthResponseDTO> refreshToken(RefreshTokenDTO request) {
        try {
            String refreshToken = request.getRefreshToken();

            if (!jwtUtil.validateToken(refreshToken)) {
                return ApiResponse.error("Token de rafraîchissement invalide");
            }

            String email = jwtUtil.extractUsername(refreshToken);
            Optional<User> userOpt = userRepository.findByEmail(email);

            if (userOpt.isEmpty()) {
                return ApiResponse.error("Utilisateur non trouvé");
            }

            User user = userOpt.get();
            String newAccessToken = jwtUtil.generateToken(user);

            AuthResponseDTO response = new AuthResponseDTO();
            response.setAccessToken(newAccessToken);
            response.setRefreshToken(refreshToken);
            response.setTokenType("Bearer");
            response.setUser(convertToUserDTO(user));

            return ApiResponse.success("Token rafraîchi avec succès", response);

        } catch (Exception e) {
            log.error("Erreur lors du rafraîchissement du token: {}", e.getMessage());
            return ApiResponse.error("Erreur lors du rafraîchissement du token");
        }
    }

    public ApiResponse<String> verifyEmail(String token) {
        try {
            String email = jwtUtil.extractUsername(token);
            Optional<User> userOpt = userRepository.findByEmail(email);

            if (userOpt.isEmpty()) {
                return ApiResponse.error("Token de vérification invalide");
            }

            User user = userOpt.get();
            user.setEmailVerified(true);
            user.setIsActive(true);
            userRepository.save(user);

            log.info("Email vérifié avec succès pour: {}", email);
            return ApiResponse.success("Email vérifié avec succès");

        } catch (Exception e) {
            log.error("Erreur lors de la vérification d'email: {}", e.getMessage());
            return ApiResponse.error("Token de vérification invalide ou expiré");
        }
    }

    public ApiResponse<String> forgotPassword(ForgotPasswordDTO request) {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());

        if (userOpt.isEmpty()) {
            // Pour des raisons de sécurité, renvoyer le même message
            return ApiResponse.success("Si cet email existe, un lien de réinitialisation a été envoyé");
        }

        User user = userOpt.get();
        try {
            emailService.sendPasswordResetEmail(user);
            log.info("Email de reset envoyé pour: {}", user.getEmail());
        } catch (Exception e) {
            log.error("Erreur envoi email reset pour {}: {}", user.getEmail(), e.getMessage());
        }

        return ApiResponse.success("Si cet email existe, un lien de réinitialisation a été envoyé");
    }

    @Transactional
    public ApiResponse<String> resetPassword(ResetPasswordDTO request) {
        try {
            String email = jwtUtil.extractUsername(request.getToken());
            Optional<User> userOpt = userRepository.findByEmail(email);

            if (userOpt.isEmpty()) {
                return ApiResponse.error("Token de réinitialisation invalide");
            }

            User user = userOpt.get();
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user);

            log.info("Mot de passe réinitialisé avec succès pour: {}", email);
            return ApiResponse.success("Mot de passe réinitialisé avec succès");

        } catch (Exception e) {
            log.error("Erreur lors de la réinitialisation du mot de passe: {}", e.getMessage());
            return ApiResponse.error("Token de réinitialisation invalide ou expiré");
        }
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        }
        throw new RuntimeException("Utilisateur non authentifié");
    }

    private UserDTO convertToUserDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setLastLogin(user.getLastLogin());
        dto.setIsActive(user.getIsActive());
        dto.setEmailVerified(user.getEmailVerified());
        return dto;
    }
}
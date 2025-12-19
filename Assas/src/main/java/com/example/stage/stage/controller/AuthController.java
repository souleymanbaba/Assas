package com.example.stage.stage.controller;

// =====================================
// IMPORTS CORRIGÉS - ATTENTION AU PACKAGE ApiResponse
// =====================================

// DTOs et entités
import com.example.stage.stage.dto.*;
// ✅ UTILISEZ LE BON PACKAGE POUR ApiResponse
// Vérifiez dans votre projet quel package contient ApiResponse
// Option 1: Si ApiResponse est dans le package 'response'
import com.example.stage.stage.response.ApiResponse;
// Option 2: Si ApiResponse est dans le package 'dto'
// import com.example.stage.stage.dto.ApiResponse;

import com.example.stage.stage.service.AuthService;

// Spring Framework
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register/client")
    @Operation(summary = "Inscription client", description = "Créer un compte client")
    public ResponseEntity<ApiResponse<String>> registerClient(@Valid @RequestBody ClientRegistrationDTO request) {
        ApiResponse<String> response = authService.registerClient(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register/admin")
    public ResponseEntity<ApiResponse<String>> registerAdmin(@Valid @RequestBody AdminCreateDTO request) {

        log.info("Tentative d'inscription professionnel: {}", request.getEmail());

        try {
            ApiResponse<String> response = authService.registeradmin(request);

            if (response.isSuccess()) {
                log.info("Inscription professionnel réussie pour: {}", request.getEmail());
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                log.warn("Inscription professionnel échouée pour {}: {}", request.getEmail(), response.getError());
                return ResponseEntity.badRequest().body(response);
            }

        } catch (DataIntegrityViolationException e) {
            log.error("Violation contrainte DB pour professionnel {}: {}", request.getEmail(), e.getMessage());

            if (e.getMessage().contains("email") || e.getMessage().contains("UK_") ||
                    e.getMessage().contains("unique") || e.getMessage().contains("Duplicate")) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Un compte avec cet email existe déjà"));
            }
            if (e.getMessage().contains("phone")) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Ce numéro de téléphone est déjà utilisé"));
            }
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Erreur de validation des données"));

        } catch (Exception e) {
            log.error("Erreur inattendue lors de l'inscription professionnel: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }

    @PostMapping("/register/professional")
    public ResponseEntity<ApiResponse<String>> registerProfessional(@Valid @RequestBody ProfessionalCreateDTO request) {

        log.info("Tentative d'inscription professionnel: {}", request.getEmail());

        try {
            ApiResponse<String> response = authService.registerProfessional(request);

            if (response.isSuccess()) {
                log.info("Inscription professionnel réussie pour: {}", request.getEmail());
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                log.warn("Inscription professionnel échouée pour {}: {}", request.getEmail(), response.getError());
                return ResponseEntity.badRequest().body(response);
            }

        } catch (DataIntegrityViolationException e) {
            log.error("Violation contrainte DB pour professionnel {}: {}", request.getEmail(), e.getMessage());

            if (e.getMessage().contains("email") || e.getMessage().contains("UK_") ||
                    e.getMessage().contains("unique") || e.getMessage().contains("Duplicate")) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Un compte avec cet email existe déjà"));
            }
            if (e.getMessage().contains("phone")) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Ce numéro de téléphone est déjà utilisé"));
            }
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Erreur de validation des données"));

        } catch (Exception e) {
            log.error("Erreur inattendue lors de l'inscription professionnel: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> login(@Valid @RequestBody LoginDTO request) {

        log.info("Tentative de connexion pour: {}", request.getEmail());

        try {
            ApiResponse<AuthResponseDTO> response = authService.login(request);

            if (response.isSuccess()) {
                log.info("Connexion réussie pour: {}", request.getEmail());
                return ResponseEntity.ok(response);
            } else {
                log.warn("Connexion échouée pour {}: {}", request.getEmail(), response.getError());

                if (response.getError().contains("email") && response.getError().contains("vérifi")) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
                } else if (response.getError().contains("désactivé")) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
                }
            }

        } catch (Exception e) {
            log.error("Erreur inattendue lors de la connexion: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponseDTO>> refreshToken(@Valid @RequestBody RefreshTokenDTO request) {

        log.info("Tentative de rafraîchissement de token");

        try {
            ApiResponse<AuthResponseDTO> response = authService.refreshToken(request);

            if (response.isSuccess()) {
                log.info("Token rafraîchi avec succès");
                return ResponseEntity.ok(response);
            } else {
                log.warn("Échec du rafraîchissement: {}", response.getError());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }

        } catch (Exception e) {
            log.error("Erreur inattendue lors du rafraîchissement: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }

    @GetMapping("/verify-email")
    public ResponseEntity<ApiResponse<String>> verifyEmail(@RequestParam String token) {

        log.info("Tentative de vérification d'email");

        if (token == null || token.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Token de vérification manquant"));
        }

        try {
            ApiResponse<String> response = authService.verifyEmail(token);

            if (response.isSuccess()) {
                log.info("Email vérifié avec succès");
                return ResponseEntity.ok(response);
            } else {
                log.warn("Échec de la vérification: {}", response.getError());
                return ResponseEntity.badRequest().body(response);
            }

        } catch (Exception e) {
            log.error("Erreur inattendue lors de la vérification: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<String>> forgotPassword(@Valid @RequestBody ForgotPasswordDTO request) {

        log.info("Demande de réinitialisation de mot de passe pour: {}", request.getEmail());

        try {
            ApiResponse<String> response = authService.forgotPassword(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            log.error("Erreur inattendue lors de la demande de réinitialisation: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<String>> resetPassword(@Valid @RequestBody ResetPasswordDTO request) {

        log.info("Tentative de réinitialisation de mot de passe");

        try {
            ApiResponse<String> response = authService.resetPassword(request);

            if (response.isSuccess()) {
                log.info("Mot de passe réinitialisé avec succès");
                return ResponseEntity.ok(response);
            } else {
                log.warn("Échec de la réinitialisation: {}", response.getError());
                return ResponseEntity.badRequest().body(response);
            }

        } catch (Exception e) {
            log.error("Erreur inattendue lors de la réinitialisation: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> health() {
        return ResponseEntity.ok(ApiResponse.success("API d'authentification opérationnelle"));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDTO>> getCurrentUser() {

        try {
            com.example.stage.stage.entity.User currentUser = authService.getCurrentUser();

            UserDTO userDTO = new UserDTO();
            userDTO.setId(currentUser.getId());
            userDTO.setEmail(currentUser.getEmail());
            userDTO.setRole(currentUser.getRole());
            userDTO.setCreatedAt(currentUser.getCreatedAt());
            userDTO.setLastLogin(currentUser.getLastLogin());
            userDTO.setIsActive(currentUser.getIsActive());
            userDTO.setEmailVerified(currentUser.getEmailVerified());

            return ResponseEntity.ok(ApiResponse.success("Profil utilisateur récupéré", userDTO));

        } catch (RuntimeException e) {
            log.warn("Tentative d'accès au profil sans authentification: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("Utilisateur non authentifié"));

        } catch (Exception e) {
            log.error("Erreur lors de la récupération du profil: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Erreur interne du serveur"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout() {
        log.info("Demande de déconnexion");
        return ResponseEntity.ok(ApiResponse.success("Déconnexion réussie"));
    }
}

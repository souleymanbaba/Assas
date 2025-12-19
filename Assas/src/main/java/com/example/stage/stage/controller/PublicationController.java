package com.example.stage.stage.controller;

import com.example.stage.stage.dto.*;
import com.example.stage.stage.response.ApiResponse;
import com.example.stage.stage.service.PublicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/publications")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PublicationController {

    private final PublicationService publicationService;

    // CREATE - Créer une nouvelle publication
    @PostMapping
    @PreAuthorize("hasRole('PROFESSIONAL') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PublicationResponseDTO>> createPublication(
            @Valid @ModelAttribute PublicationCreateDTO request,
            Authentication authentication) {

        // Vérifier que l'email correspond à l'utilisateur connecté (sauf pour admin)
        if (!hasRole(authentication, "ADMIN") && !request.getEmail().equals(authentication.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ApiResponse.error("Vous ne pouvez créer des publications que pour votre propre email"));
        }

        ApiResponse<PublicationResponseDTO> response = publicationService.createPublication(request);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST)
                .body(response);
    }

    // READ - Obtenir toutes les publications avec pagination
    @GetMapping
    public ResponseEntity<ApiResponse<Page<PublicationResponseDTO>>> getAllPublications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {

        ApiResponse<Page<PublicationResponseDTO>> response =
                publicationService.getAllPublications(page, size, sortBy, sortDirection);

        return ResponseEntity.ok(response);
    }

    // READ - Obtenir une publication par ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PublicationResponseDTO>> getPublicationById(@PathVariable Long id) {
        ApiResponse<PublicationResponseDTO> response = publicationService.getPublicationById(id);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(response);
    }

    // READ - Obtenir les publications par email
    @GetMapping("/by-email/{email}")
    public ResponseEntity<ApiResponse<Page<PublicationResponseDTO>>> getPublicationsByEmail(
            @PathVariable String email,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication) {

        // Vérifier que l'utilisateur peut accéder à ces publications
        if (!hasRole(authentication, "ADMIN") && !email.equals(authentication.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ApiResponse.error("Accès non autorisé"));
        }

        ApiResponse<Page<PublicationResponseDTO>> response =
                publicationService.getPublicationsByEmail(email, page, size);

        return ResponseEntity.ok(response);
    }

    // READ - Obtenir les publications actives
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<Page<PublicationResponseDTO>>> getActivePublications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        ApiResponse<Page<PublicationResponseDTO>> response =
                publicationService.getActivePublications(page, size);

        return ResponseEntity.ok(response);
    }

    // READ - Obtenir les publications par place
    @GetMapping("/by-place/{place}")
    public ResponseEntity<ApiResponse<Page<PublicationResponseDTO>>> getPublicationsByPlace(
            @PathVariable Integer place,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        ApiResponse<Page<PublicationResponseDTO>> response =
                publicationService.getPublicationsByPlace(place, page, size);

        return ResponseEntity.ok(response);
    }

    // UPDATE - Mettre à jour une publication
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PROFESSIONAL') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PublicationResponseDTO>> updatePublication(
            @PathVariable Long id,
            @Valid @ModelAttribute PublicationUpdateDTO request,
            Authentication authentication) {

        // Vérifier que l'utilisateur peut modifier cette publication
        if (!hasRole(authentication, "ADMIN") &&
                !publicationService.canUserModifyPublication(id, authentication.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ApiResponse.error("Vous ne pouvez modifier que vos propres publications"));
        }

        ApiResponse<PublicationResponseDTO> response = publicationService.updatePublication(id, request);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(response);
    }

    // DELETE - Supprimer une publication
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PROFESSIONAL') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<String>> deletePublication(
            @PathVariable Long id,
            Authentication authentication) {

        // Vérifier que l'utilisateur peut supprimer cette publication
        if (!hasRole(authentication, "ADMIN") &&
                !publicationService.canUserModifyPublication(id, authentication.getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ApiResponse.error("Vous ne pouvez supprimer que vos propres publications"));
        }

        ApiResponse<String> response = publicationService.deletePublication(id);
        return ResponseEntity.status(response.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(response);
    }

    // SEARCH - Recherche avancée avec filtres
    @PostMapping("/search")
    public ResponseEntity<ApiResponse<Page<PublicationResponseDTO>>> searchPublications(
            @RequestBody PublicationSearchDTO searchDTO) {

        ApiResponse<Page<PublicationResponseDTO>> response = publicationService.searchPublications(searchDTO);
        return ResponseEntity.ok(response);
    }

    // GET - Recherche simple par paramètres
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<PublicationResponseDTO>>> searchPublicationsByParams(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Integer place,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {

        PublicationSearchDTO searchDTO = new PublicationSearchDTO();
        searchDTO.setTitle(title);
        searchDTO.setEmail(email);
        searchDTO.setPlace(place);
        searchDTO.setStatus(status);
        searchDTO.setPage(page);
        searchDTO.setSize(size);
        searchDTO.setSortBy(sortBy);
        searchDTO.setSortDirection(sortDirection);

        ApiResponse<Page<PublicationResponseDTO>> response = publicationService.searchPublications(searchDTO);
        return ResponseEntity.ok(response);
    }

    // GET - Obtenir les statistiques des publications
    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PublicationStatsDTO>> getPublicationStats(
            @RequestParam(required = false) String email) {

        ApiResponse<PublicationStatsDTO> response = publicationService.getPublicationStats(email);
        return ResponseEntity.ok(response);
    }

    // GET - Obtenir mes publications (utilisateur connecté)
    @GetMapping("/my-publications")
    @PreAuthorize("hasRole('PROFESSIONAL')")
    public ResponseEntity<ApiResponse<Page<PublicationResponseDTO>>> getMyPublications(
            Authentication authentication,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        String email = authentication.getName();
        ApiResponse<Page<PublicationResponseDTO>> response =
                publicationService.getPublicationsByEmail(email, page, size);

        return ResponseEntity.ok(response);
    }

    // GET - Obtenir les publications expirées
    @GetMapping("/expired")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Page<PublicationResponseDTO>>> getExpiredPublications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PublicationSearchDTO searchDTO = new PublicationSearchDTO();
        searchDTO.setStatus("expired");
        searchDTO.setPage(page);
        searchDTO.setSize(size);

        ApiResponse<Page<PublicationResponseDTO>> response = publicationService.searchPublications(searchDTO);
        return ResponseEntity.ok(response);
    }

    // GET - Obtenir les publications à venir
    @GetMapping("/upcoming")
    public ResponseEntity<ApiResponse<Page<PublicationResponseDTO>>> getUpcomingPublications(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        PublicationSearchDTO searchDTO = new PublicationSearchDTO();
        searchDTO.setStatus("upcoming");
        searchDTO.setPage(page);
        searchDTO.setSize(size);

        ApiResponse<Page<PublicationResponseDTO>> response = publicationService.searchPublications(searchDTO);
        return ResponseEntity.ok(response);
    }

    // Méthode utilitaire pour vérifier les rôles
    private boolean hasRole(Authentication authentication, String role) {
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_" + role));
    }
}
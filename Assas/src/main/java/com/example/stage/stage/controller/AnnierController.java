package com.example.stage.stage.controller;

import com.example.stage.stage.dto.AnnierDto;
import com.example.stage.stage.service.AnnierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/anniers")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class AnnierController {

    private final AnnierService annierService;

    // CREATE
    @PostMapping
    public ResponseEntity<?> createAnnier(@Valid @RequestBody AnnierDto annierDto) {
        try {
            AnnierDto createdAnnier = annierService.createAnnier(annierDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAnnier);
        } catch (Exception e) {
            log.error("Erreur lors de la création de l'annier: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erreur: " + e.getMessage());
        }
    }

    // CREATE WITH IMAGE
    @PostMapping(value = "/with-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createAnnierWithImage(
            @RequestParam("nom") String nom,
            @RequestParam("email") String email,
            @RequestParam(value = "numeroTelephone", required = false) String numeroTelephone,
            @RequestParam(value = "numeroWhatsapp", required = false) String numeroWhatsapp,
            @RequestParam(value = "specialite", required = false) String specialite,
            @RequestPart(value = "image", required = false) MultipartFile imageFile,
            @RequestParam(value = "matrucule", required = false) String matrucule
            ) {
        try {
            log.info("Création d'annier avec image - Nom: {}, Email: {}", nom, email);

            // Créer le DTO manuellement
            AnnierDto annierDto = new AnnierDto();
            annierDto.setNom(nom);
            annierDto.setEmail(email);
            annierDto.setNumeroTelephone(numeroTelephone);
            annierDto.setNumeroWhatsapp(numeroWhatsapp);
            annierDto.setSpecialite(specialite);
            annierDto.setMatrucule(matrucule);

            // Validation manuelle
            if (nom == null || nom.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Erreur: Le nom est obligatoire");
            }

            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Erreur: L'email est obligatoire");
            }

            if (imageFile != null) {
                log.info("Fichier reçu - Nom: {}, Taille: {}, Type: {}",
                        imageFile.getOriginalFilename(),
                        imageFile.getSize(),
                        imageFile.getContentType());
            }

            AnnierDto createdAnnier = annierService.createAnnierWithImage(annierDto, imageFile);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAnnier);

        } catch (Exception e) {
            log.error("Erreur lors de la création de l'annier avec image: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erreur: " + e.getMessage());
        }
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<?> getAllAnniers() {
        try {
            List<AnnierDto> anniers = annierService.getAllAnniers();
            return ResponseEntity.ok(anniers);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des anniers: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur: " + e.getMessage());
        }
    }

    // READ ALL PAGINATED
    @GetMapping("/paginated")
    public ResponseEntity<?> getAllAnniersPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<AnnierDto> anniers = annierService.getAllAnniersPaginated(pageable);
            return ResponseEntity.ok(anniers);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération paginée: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur: " + e.getMessage());
        }
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getAnnierById(@PathVariable Long id) {
        try {
            AnnierDto annier = annierService.getAnnierById(id);
            return ResponseEntity.ok(annier);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération de l'annier: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Erreur: " + e.getMessage());
        }
    }

    // READ BY EMAIL
    @GetMapping("/email/{email}")
    public ResponseEntity<?> getAnnierByEmail(@PathVariable String email) {
        try {
            AnnierDto annier = annierService.getAnnierByEmail(email);
            return ResponseEntity.ok(annier);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération par email: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Erreur: " + e.getMessage());
        }
    }

    // SEARCH BY SPECIALITE
    @GetMapping("/search/specialite")
    public ResponseEntity<?> searchBySpecialite(@RequestParam String specialite) {
        try {
            List<AnnierDto> anniers = annierService.searchBySpecialite(specialite);
            return ResponseEntity.ok(anniers);
        } catch (Exception e) {
            log.error("Erreur lors de la recherche par spécialité: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur: " + e.getMessage());
        }
    }

    // SEARCH BY NAME
    @GetMapping("/search/nom")
    public ResponseEntity<?> searchByNom(@RequestParam String nom) {
        try {
            List<AnnierDto> anniers = annierService.searchByNom(nom);
            return ResponseEntity.ok(anniers);
        } catch (Exception e) {
            log.error("Erreur lors de la recherche par nom: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur: " + e.getMessage());
        }
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAnnier(
            @PathVariable Long id,
            @Valid @RequestBody AnnierDto annierDto) {
        try {
            AnnierDto updatedAnnier = annierService.updateAnnier(id, annierDto);
            return ResponseEntity.ok(updatedAnnier);
        } catch (Exception e) {
            log.error("Erreur lors de la mise à jour: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erreur: " + e.getMessage());
        }
    }

    // UPDATE WITH IMAGE
    @PutMapping(value = "/{id}/with-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateAnnierWithImage(
            @PathVariable Long id,
            @RequestPart("annier") @Valid AnnierDto annierDto,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        try {
            AnnierDto updatedAnnier = annierService.updateAnnierWithImage(id, annierDto, imageFile);
            return ResponseEntity.ok(updatedAnnier);
        } catch (Exception e) {
            log.error("Erreur lors de la mise à jour avec image: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erreur: " + e.getMessage());
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAnnier(@PathVariable Long id) {
        try {
            annierService.deleteAnnier(id);
            return ResponseEntity.ok("Annier supprimé avec succès");
        } catch (Exception e) {
            log.error("Erreur lors de la suppression: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Erreur: " + e.getMessage());
        }
    }

    // GET IMAGE
    @GetMapping("/images/{filename}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) {
        try {
            byte[] image = annierService.getImage(filename);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(image);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération de l'image: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
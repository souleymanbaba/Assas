package com.example.stage.stage.controller;

import com.example.stage.stage.dto.LoiDto;
import com.example.stage.stage.services.LoiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/lois")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin("*")
public class LoiController {

    private final LoiService loiService;

    // CREATE
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createLoi(
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("titre") String titre,
            @RequestPart(value = "pdf", required = false) MultipartFile pdfFile) {
        try {
            LoiDto createdLoi = loiService.createLoi(categoryId, titre, pdfFile);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdLoi);
        } catch (Exception e) {
            log.error("Erreur lors de la création de la loi: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erreur: " + e.getMessage());
        }
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<?> getAllLois() {
        try {
            List<LoiDto> lois = loiService.getAllLois();
            return ResponseEntity.ok(lois);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des lois: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur: " + e.getMessage());
        }
    }

    // READ ALL PAGINATED
    @GetMapping("/paginated")
    public ResponseEntity<?> getAllLoisPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<LoiDto> lois = loiService.getAllLoisPaginated(pageable);
            return ResponseEntity.ok(lois);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération paginée: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur: " + e.getMessage());
        }
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getLoiById(@PathVariable Long id) {
        try {
            LoiDto loi = loiService.getLoiById(id);
            return ResponseEntity.ok(loi);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération de la loi: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Erreur: " + e.getMessage());
        }
    }

    // READ BY CATEGORY
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getLoisByCategory(@PathVariable Long categoryId) {
        try {
            List<LoiDto> lois = loiService.getLoisByCategory(categoryId);
            return ResponseEntity.ok(lois);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération des lois par catégorie: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erreur: " + e.getMessage());
        }
    }

    // SEARCH BY TITLE
    @GetMapping("/search")
    public ResponseEntity<?> searchLois(@RequestParam String titre) {
        try {
            List<LoiDto> lois = loiService.searchLoisByTitle(titre);
            return ResponseEntity.ok(lois);
        } catch (Exception e) {
            log.error("Erreur lors de la recherche: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur: " + e.getMessage());
        }
    }

    // UPDATE
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateLoi(
            @PathVariable Long id,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("titre") String titre,
            @RequestPart(value = "pdf", required = false) MultipartFile pdfFile) {
        try {
            LoiDto updatedLoi = loiService.updateLoi(id, categoryId, titre, pdfFile);
            return ResponseEntity.ok(updatedLoi);
        } catch (Exception e) {
            log.error("Erreur lors de la mise à jour: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erreur: " + e.getMessage());
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLoi(@PathVariable Long id) {
        try {
            loiService.deleteLoi(id);
            return ResponseEntity.ok("Loi supprimée avec succès");
        } catch (Exception e) {
            log.error("Erreur lors de la suppression: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Erreur: " + e.getMessage());
        }
    }

    // GET PDF
    @GetMapping("/pdf/{filename}")
    public ResponseEntity<byte[]> getPdf(@PathVariable String filename) {
        try {
            byte[] pdf = loiService.getPdf(filename);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                    .body(pdf);
        } catch (Exception e) {
            log.error("Erreur lors de la récupération du PDF: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
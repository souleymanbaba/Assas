package com.example.stage.stage.controller;

import com.example.stage.stage.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class FileControllers {

    private final FileService fileService;

    /**
     * Servir les images des formations
     */
    @GetMapping("/formations/{filename:.+}")
    public ResponseEntity<Resource> getFormationImage(@PathVariable String filename) {
        try {
            Path filePath = fileService.getFilePath(filename);
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                log.warn("Fichier non trouvé: {}", filename);
                return ResponseEntity.notFound().build();
            }

            // Déterminer le type de contenu
            String contentType = java.nio.file.Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                    .body(resource);

        } catch (Exception e) {
            log.error("Erreur lors de la récupération du fichier: {}", filename, e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
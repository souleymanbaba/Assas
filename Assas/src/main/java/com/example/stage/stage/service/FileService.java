package com.example.stage.stage.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@Slf4j
public class FileService {

    @Value("${app.upload.dir:uploads/formations}")
    private String uploadDir;

    @Value("${app.base.url:http://localhost:8888}")
    private String baseUrl;

    /**
     * Sauvegarder un fichier image
     */
    public String saveImage(MultipartFile file, String language) throws IOException {
        validateImageFile(file);

        // Créer le répertoire s'il n'existe pas
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
            log.info("Répertoire créé: {}", uploadPath);
        }

        // Générer un nom de fichier unique
        String originalFilename = file.getOriginalFilename();
        String extension = getFileExtension(originalFilename);
        String uniqueFilename = generateUniqueFilename(language, extension);

        // Chemin complet du fichier
        Path filePath = uploadPath.resolve(uniqueFilename);

        // Sauvegarder le fichier
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        log.info("Fichier sauvegardé: {}", filePath);

        // Retourner l'URL relative
        return "/api/files/formations/" + uniqueFilename;
    }

    /**
     * Supprimer un fichier image
     */
    public boolean deleteImage(String imageUrl) {
        try {
            if (imageUrl == null || !imageUrl.startsWith("/api/files/formations/")) {
                return false;
            }

            String filename = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
            Path filePath = Paths.get(uploadDir, filename);

            if (Files.exists(filePath)) {
                Files.delete(filePath);
                log.info("Fichier supprimé: {}", filePath);
                return true;
            }

            return false;
        } catch (IOException e) {
            log.error("Erreur lors de la suppression du fichier: {}", imageUrl, e);
            return false;
        }
    }

    /**
     * Valider le fichier image
     */
    private void validateImageFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("Le fichier est vide");
        }

        // Vérifier la taille (max 5MB)
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new IOException("Le fichier est trop volumineux (max 5MB)");
        }

        // Vérifier le type de fichier
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IOException("Le fichier doit être une image");
        }

        // Types autorisés
        if (!contentType.equals("image/jpeg") &&
                !contentType.equals("image/png") &&
                !contentType.equals("image/webp")) {
            throw new IOException("Format d'image non supporté. Utilisez JPEG, PNG ou WebP");
        }
    }

    /**
     * Obtenir l'extension du fichier
     */
    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf("."));
    }

    /**
     * Générer un nom de fichier unique
     */
    private String generateUniqueFilename(String language, String extension) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        return String.format("formation_%s_%s_%s%s", language, timestamp, uuid, extension);
    }

    /**
     * Obtenir le chemin physique d'un fichier
     */
    public Path getFilePath(String filename) {
        return Paths.get(uploadDir, filename);
    }
}
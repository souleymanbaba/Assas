package com.example.stage.stage.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    @Value("${app.base.url:http://31.97.69.64:192989}")
    private String baseUrl;

    public String storeFile(MultipartFile file, String folder) throws IOException {
        // Créer le répertoire s'il n'existe pas
        Path uploadPath = Paths.get(uploadDir, folder);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Générer un nom unique pour le fichier
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

        // Sauvegarder le fichier
        Path filePath = uploadPath.resolve(uniqueFileName);
        Files.copy(file.getInputStream(), filePath);

        // Retourner l'URL d'accès au fichier
        return baseUrl + "/uploads/" + folder + "/" + uniqueFileName;
    }

    public void deleteFile(String fileUrl) {
        try {
            if (fileUrl != null && fileUrl.startsWith(baseUrl)) {
                String relativePath = fileUrl.replace(baseUrl + "/", "");
                Path filePath = Paths.get(relativePath);
                Files.deleteIfExists(filePath);
            }
        } catch (IOException e) {
            // Log l'erreur mais ne pas faire échouer l'opération
            System.err.println("Erreur lors de la suppression du fichier: " + e.getMessage());
        }
    }
}

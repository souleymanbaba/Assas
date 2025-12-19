package com.example.stage.stage.services;

import com.example.stage.stage.dto.LoiDto;
import com.example.stage.stage.entity.Loi;
import com.example.stage.stage.entity.Category;
import com.example.stage.stage.repository.LoiRepository;
import com.example.stage.stage.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoiService {

    private final LoiRepository loiRepository;
    private final CategoryRepository categoryRepository;

    @Value("${file.upload-dir.pdf:uploads/lois}")
    private String uploadDir;

    @Value("${app.base-url:http://31.97.69.64:192989}")
    private String baseUrl;

    // CREATE WITH PDF
    @Transactional
    public LoiDto createLoi(Long categoryId, String titre, MultipartFile pdfFile) {
        log.info("Création d'une nouvelle loi: {}", titre);

        // Vérifier que la catégorie existe
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée avec l'ID: " + categoryId));

        // Valider le titre
        if (titre == null || titre.trim().isEmpty()) {
            throw new RuntimeException("Le titre est obligatoire");
        }

        Loi loi = new Loi();
        loi.setCategory(category);
        loi.setTitre(titre.trim());

        // Sauvegarder le PDF si fourni
        if (pdfFile != null && !pdfFile.isEmpty()) {
            String[] pdfInfo = savePdf(pdfFile);
            loi.setPdfUrl(pdfInfo[0]);
            loi.setPdfFilename(pdfInfo[1]);
        }

        loi = loiRepository.save(loi);

        log.info("Loi créée avec l'ID: {}", loi.getId());
        return convertToDto(loi);
    }

    // READ ALL
    @Transactional(readOnly = true)
    public List<LoiDto> getAllLois() {
        log.info("Récupération de toutes les lois");

        List<Loi> lois = loiRepository.findAllByOrderByDateCreationDesc();
        return lois.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // READ ALL PAGINATED
    @Transactional(readOnly = true)
    public Page<LoiDto> getAllLoisPaginated(Pageable pageable) {
        log.info("Récupération paginée des lois");

        Page<Loi> lois = loiRepository.findAll(pageable);
        return lois.map(this::convertToDto);
    }

    // READ BY ID
    @Transactional(readOnly = true)
    public LoiDto getLoiById(Long id) {
        log.info("Récupération de la loi avec l'ID: {}", id);

        Loi loi = loiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loi non trouvée avec l'ID: " + id));

        return convertToDto(loi);
    }

    // READ BY CATEGORY
    @Transactional(readOnly = true)
    public List<LoiDto> getLoisByCategory(Long categoryId) {
        log.info("Récupération des lois pour la catégorie: {}", categoryId);

        // Vérifier que la catégorie existe
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée avec l'ID: " + categoryId));

        List<Loi> lois = loiRepository.findByCategoryId(categoryId);
        return lois.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // SEARCH BY TITLE
    @Transactional(readOnly = true)
    public List<LoiDto> searchLoisByTitle(String titre) {
        log.info("Recherche de lois par titre: {}", titre);

        List<Loi> lois = loiRepository.findByTitreContainingIgnoreCase(titre);
        return lois.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // UPDATE
    @Transactional
    public LoiDto updateLoi(Long id, Long categoryId, String titre, MultipartFile pdfFile) {
        log.info("Mise à jour de la loi avec l'ID: {}", id);

        Loi existingLoi = loiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loi non trouvée avec l'ID: " + id));

        // Vérifier que la nouvelle catégorie existe
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée avec l'ID: " + categoryId));

        // Valider le titre
        if (titre == null || titre.trim().isEmpty()) {
            throw new RuntimeException("Le titre est obligatoire");
        }

        // Sauvegarder le nouveau PDF si fourni
        if (pdfFile != null && !pdfFile.isEmpty()) {
            // Supprimer l'ancien PDF si il existe
            if (existingLoi.getPdfUrl() != null) {
                deletePdf(existingLoi.getPdfUrl());
            }
            String[] pdfInfo = savePdf(pdfFile);
            existingLoi.setPdfUrl(pdfInfo[0]);
            existingLoi.setPdfFilename(pdfInfo[1]);
        }

        // Mettre à jour les champs
        existingLoi.setCategory(category);
        existingLoi.setTitre(titre.trim());

        existingLoi = loiRepository.save(existingLoi);

        log.info("Loi mise à jour avec succès: {}", id);
        return convertToDto(existingLoi);
    }

    // DELETE
    @Transactional
    public void deleteLoi(Long id) {
        log.info("Suppression de la loi avec l'ID: {}", id);

        Loi loi = loiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Loi non trouvée avec l'ID: " + id));

        // Supprimer le PDF associé si il existe
        if (loi.getPdfUrl() != null) {
            deletePdf(loi.getPdfUrl());
        }

        loiRepository.deleteById(id);
        log.info("Loi supprimée avec succès: {}", id);
    }

    // PDF MANAGEMENT METHODS
    private String[] savePdf(MultipartFile file) {
        try {
            // Validation du fichier
            if (file.isEmpty()) {
                throw new RuntimeException("Le fichier est vide");
            }

            // Vérifier le type de fichier
            String contentType = file.getContentType();
            if (contentType == null || !contentType.equals("application/pdf")) {
                throw new RuntimeException("Le fichier doit être un PDF");
            }

            // Créer le dossier s'il n'existe pas
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
                log.info("Dossier créé: {}", uploadPath.toAbsolutePath());
            }

            // Générer un nom de fichier unique
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null) {
                originalFilename = "document.pdf";
            }

            String filename = UUID.randomUUID().toString() + ".pdf";

            // Sauvegarder le fichier
            Path filePath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath);

            log.info("PDF sauvegardé: {}", filePath.toAbsolutePath());

            String pdfUrl = baseUrl + "/api/lois/pdf/" + filename;

            // Retourner [URL, nom_original]
            return new String[]{pdfUrl, originalFilename};

        } catch (IOException e) {
            log.error("Erreur lors de la sauvegarde du PDF: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de la sauvegarde du PDF: " + e.getMessage());
        }
    }

    private void deletePdf(String pdfUrl) {
        try {
            String filename = pdfUrl.substring(pdfUrl.lastIndexOf("/") + 1);
            Path filePath = Paths.get(uploadDir).resolve(filename);

            if (Files.exists(filePath)) {
                Files.delete(filePath);
                log.info("PDF supprimé: {}", filename);
            }
        } catch (IOException e) {
            log.error("Erreur lors de la suppression du PDF: {}", e.getMessage());
        }
    }

    public byte[] getPdf(String filename) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(filename);
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            log.error("Erreur lors de la lecture du PDF: {}", e.getMessage());
            throw new RuntimeException("PDF non trouvé");
        }
    }

    // CONVERSION METHOD
    private LoiDto convertToDto(Loi loi) {
        LoiDto dto = new LoiDto();
        dto.setId(loi.getId());
        dto.setCategoryId(loi.getCategory().getId());
        dto.setCategoryName(loi.getCategory().getName());
        dto.setTitre(loi.getTitre());
        dto.setPdfUrl(loi.getPdfUrl());
        dto.setPdfFilename(loi.getPdfFilename());
        dto.setDateCreation(loi.getDateCreation());
        return dto;
    }
}
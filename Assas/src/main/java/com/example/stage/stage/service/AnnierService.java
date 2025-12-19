package com.example.stage.stage.service;

import com.example.stage.stage.dto.AnnierDto;
import com.example.stage.stage.entity.Annier;
import com.example.stage.stage.repository.AnnierRepository;
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
public class AnnierService {

    private final AnnierRepository annierRepository;

    @Value("${file.upload-dir:uploads/anniers}")
    private String uploadDir;


    @Value("${app.base-url:http://31.97.69.64:192989}")
    private String baseUrl;

    // CREATE
    @Transactional
    public AnnierDto createAnnier(AnnierDto annierDto) {
        log.info("Création d'un nouvel annier: {}", annierDto.getEmail());

        // Vérifier si l'email existe déjà
        if (annierRepository.existsByEmail(annierDto.getEmail())) {
            throw new RuntimeException("Un annier avec cet email existe déjà");
        }

        Annier annier = convertToEntity(annierDto);
        annier = annierRepository.save(annier);

        log.info("Annier créé avec l'ID: {}", annier.getId());
        return convertToDto(annier);
    }

    // CREATE WITH IMAGE
    @Transactional
    public AnnierDto createAnnierWithImage(AnnierDto annierDto, MultipartFile imageFile) {
        log.info("Création d'un nouvel annier avec image: {}", annierDto.getEmail());

        // Vérifier si l'email existe déjà
        if (annierRepository.existsByEmail(annierDto.getEmail())) {
            throw new RuntimeException("Un annier avec cet email existe déjà");
        }

        // Sauvegarder l'image si fournie
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = saveImage(imageFile);
            annierDto.setImageUrl(imageUrl);
        }

        Annier annier = convertToEntity(annierDto);
        annier = annierRepository.save(annier);

        log.info("Annier créé avec image et ID: {}", annier.getId());
        return convertToDto(annier);
    }

    // READ ALL
    @Transactional(readOnly = true)
    public List<AnnierDto> getAllAnniers() {
        log.info("Récupération de tous les anniers");

        List<Annier> anniers = annierRepository.findAll();
        return anniers.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // READ ALL PAGINATED
    @Transactional(readOnly = true)
    public Page<AnnierDto> getAllAnniersPaginated(Pageable pageable) {
        log.info("Récupération paginée des anniers");

        Page<Annier> anniers = annierRepository.findAll(pageable);
        return anniers.map(this::convertToDto);
    }

    // READ BY ID
    @Transactional(readOnly = true)
    public AnnierDto getAnnierById(Long id) {
        log.info("Récupération de l'annier avec l'ID: {}", id);

        Annier annier = annierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Annier non trouvé avec l'ID: " + id));

        return convertToDto(annier);
    }

    // READ BY EMAIL
    @Transactional(readOnly = true)
    public AnnierDto getAnnierByEmail(String email) {
        log.info("Récupération de l'annier avec l'email: {}", email);

        Annier annier = annierRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Annier non trouvé avec l'email: " + email));

        return convertToDto(annier);
    }

    // SEARCH BY SPECIALITE
    @Transactional(readOnly = true)
    public List<AnnierDto> searchBySpecialite(String specialite) {
        log.info("Recherche d'anniers par spécialité: {}", specialite);

        List<Annier> anniers = annierRepository.findBySpecialiteContainingIgnoreCase(specialite);
        return anniers.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // SEARCH BY NAME
    @Transactional(readOnly = true)
    public List<AnnierDto> searchByNom(String nom) {
        log.info("Recherche d'anniers par nom: {}", nom);

        List<Annier> anniers = annierRepository.findByNomContainingIgnoreCase(nom);
        return anniers.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // UPDATE
    @Transactional
    public AnnierDto updateAnnier(Long id, AnnierDto annierDto) {
        log.info("Mise à jour de l'annier avec l'ID: {}", id);

        Annier existingAnnier = annierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Annier non trouvé avec l'ID: " + id));

        // Vérifier si le nouvel email existe déjà (sauf pour l'annier actuel)
        if (!existingAnnier.getEmail().equals(annierDto.getEmail()) &&
                annierRepository.existsByEmail(annierDto.getEmail())) {
            throw new RuntimeException("Un annier avec cet email existe déjà");
        }

        // Mettre à jour les champs
        existingAnnier.setNom(annierDto.getNom());
        existingAnnier.setEmail(annierDto.getEmail());
        existingAnnier.setNumeroTelephone(annierDto.getNumeroTelephone());
        existingAnnier.setNumeroWhatsapp(annierDto.getNumeroWhatsapp());
        existingAnnier.setSpecialite(annierDto.getSpecialite());
        existingAnnier.setMatrucule(annierDto.getMatrucule());


        // Conserver l'ancienne image si pas de nouvelle URL fournie
        if (annierDto.getImageUrl() != null) {
            existingAnnier.setImageUrl(annierDto.getImageUrl());
        }

        existingAnnier = annierRepository.save(existingAnnier);

        log.info("Annier mis à jour avec succès: {}", id);
        return convertToDto(existingAnnier);
    }

    // UPDATE WITH IMAGE
    @Transactional
    public AnnierDto updateAnnierWithImage(Long id, AnnierDto annierDto, MultipartFile imageFile) {
        log.info("Mise à jour de l'annier avec image, ID: {}", id);

        Annier existingAnnier = annierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Annier non trouvé avec l'ID: " + id));

        // Vérifier si le nouvel email existe déjà (sauf pour l'annier actuel)
        if (!existingAnnier.getEmail().equals(annierDto.getEmail()) &&
                annierRepository.existsByEmail(annierDto.getEmail())) {
            throw new RuntimeException("Un annier avec cet email existe déjà");
        }

        // Sauvegarder la nouvelle image si fournie
        if (imageFile != null && !imageFile.isEmpty()) {
            // Supprimer l'ancienne image si elle existe
            if (existingAnnier.getImageUrl() != null) {
                deleteImage(existingAnnier.getImageUrl());
            }
            String newImageUrl = saveImage(imageFile);
            annierDto.setImageUrl(newImageUrl);
        }

        // Mettre à jour les champs
        existingAnnier.setNom(annierDto.getNom());
        existingAnnier.setEmail(annierDto.getEmail());
        existingAnnier.setNumeroTelephone(annierDto.getNumeroTelephone());
        existingAnnier.setNumeroWhatsapp(annierDto.getNumeroWhatsapp());
        existingAnnier.setSpecialite(annierDto.getSpecialite());

        if (annierDto.getImageUrl() != null) {
            existingAnnier.setImageUrl(annierDto.getImageUrl());
        }

        existingAnnier = annierRepository.save(existingAnnier);

        log.info("Annier mis à jour avec image avec succès: {}", id);
        return convertToDto(existingAnnier);
    }

    // DELETE
    @Transactional
    public void deleteAnnier(Long id) {
        log.info("Suppression de l'annier avec l'ID: {}", id);

        Annier annier = annierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Annier non trouvé avec l'ID: " + id));

        // Supprimer l'image associée si elle existe
        if (annier.getImageUrl() != null) {
            deleteImage(annier.getImageUrl());
        }

        annierRepository.deleteById(id);
        log.info("Annier supprimé avec succès: {}", id);
    }

    // IMAGE MANAGEMENT METHODS
    private String saveImage(MultipartFile file) {
        try {
            // Validation du fichier
            if (file.isEmpty()) {
                throw new RuntimeException("Le fichier est vide");
            }

            // Vérifier le type de fichier
            String contentType = file.getContentType();
            if (contentType == null || (!contentType.startsWith("image/"))) {
                throw new RuntimeException("Le fichier doit être une image");
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
                originalFilename = "image.jpg";
            }

            String extension = "";
            if (originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            } else {
                extension = ".jpg"; // Extension par défaut
            }

            String filename = UUID.randomUUID().toString() + extension;

            // Sauvegarder le fichier
            Path filePath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath);

            log.info("Image sauvegardée: {}", filePath.toAbsolutePath());

            // Retourner l'URL d'accès
            return baseUrl + "/api/anniers/images/" + filename;

        } catch (IOException e) {
            log.error("Erreur lors de la sauvegarde de l'image: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de la sauvegarde de l'image: " + e.getMessage());
        }
    }

    private void deleteImage(String imageUrl) {
        try {
            // Extraire le nom du fichier de l'URL
            String filename = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
            Path filePath = Paths.get(uploadDir).resolve(filename);

            if (Files.exists(filePath)) {
                Files.delete(filePath);
                log.info("Image supprimée: {}", filename);
            }
        } catch (IOException e) {
            log.error("Erreur lors de la suppression de l'image: {}", e.getMessage());
        }
    }

    public byte[] getImage(String filename) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(filename);
            return Files.readAllBytes(filePath);
        } catch (IOException e) {
            log.error("Erreur lors de la lecture de l'image: {}", e.getMessage());
            throw new RuntimeException("Image non trouvée");
        }
    }

    // CONVERSION METHODS
    private AnnierDto convertToDto(Annier annier) {
        AnnierDto dto = new AnnierDto();
        dto.setId(annier.getId());
        dto.setNom(annier.getNom());
        dto.setEmail(annier.getEmail());
        dto.setNumeroTelephone(annier.getNumeroTelephone());
        dto.setNumeroWhatsapp(annier.getNumeroWhatsapp());
        dto.setSpecialite(annier.getSpecialite());
        dto.setImageUrl(annier.getImageUrl());
        dto.setMatrucule(annier.getMatrucule());
        return dto;
    }

    private Annier convertToEntity(AnnierDto dto) {
        Annier annier = new Annier();
        annier.setNom(dto.getNom());
        annier.setEmail(dto.getEmail());
        annier.setNumeroTelephone(dto.getNumeroTelephone());
        annier.setNumeroWhatsapp(dto.getNumeroWhatsapp());
        annier.setSpecialite(dto.getSpecialite());
        annier.setImageUrl(dto.getImageUrl());
        annier.setMatrucule(dto.getMatrucule());
        return annier;
    }
}
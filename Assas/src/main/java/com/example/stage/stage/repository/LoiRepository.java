package com.example.stage.stage.repository;

import com.example.stage.stage.entity.Loi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoiRepository extends JpaRepository<Loi, Long> {

    // Trouver par catégorie
    List<Loi> findByCategoryId(Long categoryId);

    Page<Loi> findByCategoryId(Long categoryId, Pageable pageable);

    // Recherche par titre
    List<Loi> findByTitreContainingIgnoreCase(String titre);

    // Toutes les lois triées par date
    List<Loi> findAllByOrderByDateCreationDesc();
}
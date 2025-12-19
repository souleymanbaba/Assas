package com.example.stage.stage.repository;


import com.example.stage.stage.entity.Annier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnnierRepository extends JpaRepository<Annier, Long> {

    Optional<Annier> findByEmail(String email);

    List<Annier> findBySpecialiteContainingIgnoreCase(String specialite);

    List<Annier> findByNomContainingIgnoreCase(String nom);

    boolean existsByEmail(String email);
}
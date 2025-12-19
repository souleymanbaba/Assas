package com.example.stage.stage.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "lois")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Loi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_loi")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categorie", nullable = false)
    private Category category;

    @Column(name = "titre", nullable = false, length = 200)
    private String titre;

    @Column(name = "pdf_url", length = 500)
    private String pdfUrl;

    @Column(name = "pdf_filename", length = 255)
    private String pdfFilename;

    @Column(name = "date_creation")
    private LocalDateTime dateCreation = LocalDateTime.now();

    @PrePersist
    protected void onCreate() {
        dateCreation = LocalDateTime.now();
    }
}
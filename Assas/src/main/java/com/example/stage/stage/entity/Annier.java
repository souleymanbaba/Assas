package com.example.stage.stage.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "anniers")
public class Annier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_annier")
    private Long id;

    @Column(name = "nom", nullable = false, length = 100)
    private String nom;

    @Column(name = "matrucule", nullable = false, unique = true, length = 150)
    private String matrucule;


    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "numero_telephone", length = 20)
    private String numeroTelephone;

    @Column(name = "numero_whatsapp", length = 20)
    private String numeroWhatsapp;

    @Column(name = "specialite", length = 100)
    private String specialite;

    @Column(name = "image_url", length = 500)
    private String imageUrl;
}
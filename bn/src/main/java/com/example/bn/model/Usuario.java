package com.example.bn.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Genera el ID automáticamente
    private String id;
    
    @Column(unique = true, nullable = false)
    private String usuario;
    
    @Column(nullable = false)
    private String password;
    
    private String nombre;

    @Enumerated(EnumType.STRING)
    private Role rol;
}
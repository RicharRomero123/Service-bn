package com.example.bn.model;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "reclamos")
public class Reclamo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String nroReclamo;
    private String tipoDocumento;
    private String documentoCliente;
    private boolean completado; // Será true cuando TODOS sus tickets estén gestionados

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaTrx; 

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "reclamo_id")
    private List<TicketDetalle> tickets;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
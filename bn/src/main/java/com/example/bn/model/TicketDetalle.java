package com.example.bn.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "ticket_detalles")
public class TicketDetalle {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //

    private String nroTicket;
    private Double importe;
    
    // Campos que se llenarán en la gestión individual
    private String idUnico;      // ID Único por ticket
    private String codDevolucion; // Enlazado al ID Único
    private String estadoTicket;
    private boolean gestionado;   // Para saber si este ticket ya se procesó
}
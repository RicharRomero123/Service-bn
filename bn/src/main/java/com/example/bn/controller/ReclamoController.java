package com.example.bn.controller;

import com.example.bn.model.Reclamo;
import com.example.bn.model.TicketDetalle;
import com.example.bn.service.ReclamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reclamos")
@CrossOrigin(origins = "*")
public class ReclamoController {

    @Autowired
    private ReclamoService reclamoService;

    // --- ENDPOINTS DE CONSULTA (GET) ---

    /**
     * Obtiene todos los reclamos con paginación y filtros.
     * Ejemplo: /api/reclamos?page=0&size=10&completado=false
     */
    @GetMapping
    public ResponseEntity<Page<Reclamo>> obtenerTodos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Boolean completado) {
        
        // Usamos el método flexible del service que ya ordena por más recientes
        return ResponseEntity.ok(reclamoService.listarPorEstado(page, size, completado));
    }

    // --- ENDPOINTS DE CREACIÓN Y GESTIÓN ---

    @PostMapping
    public Reclamo crearReclamo(@RequestBody Reclamo reclamo) {
        reclamo.setCompletado(false); 
        return reclamoService.guardar(reclamo);
    }

    @PutMapping("/{id}/gestionar")
    public ResponseEntity<?> gestionarReclamo(@PathVariable String id, @RequestBody Reclamo datosGestion) {
        try {
            return reclamoService.obtenerPorId(id).map(existente -> {
                
                // 1. Cabecera
                if (datosGestion.getTipoDocumento() != null) existente.setTipoDocumento(datosGestion.getTipoDocumento());
                if (datosGestion.getDocumentoCliente() != null) existente.setDocumentoCliente(datosGestion.getDocumentoCliente());
                if (datosGestion.getNroReclamo() != null) existente.setNroReclamo(datosGestion.getNroReclamo());
                if (datosGestion.getFechaTrx() != null) existente.setFechaTrx(datosGestion.getFechaTrx());
                
                // 2. Tickets
                if (datosGestion.getTickets() != null && existente.getTickets() != null) {
                    for (TicketDetalle ticketDB : existente.getTickets()) {
                        datosGestion.getTickets().stream()
                            .filter(t -> t.getId() != null && t.getId().equals(ticketDB.getId()))
                            .findFirst()
                            .ifPresent(datosNuevos -> {
                                ticketDB.setIdUnico(datosNuevos.getIdUnico() != null ? datosNuevos.getIdUnico() : ticketDB.getIdUnico());
                                ticketDB.setEstadoTicket(datosNuevos.getEstadoTicket() != null ? datosNuevos.getEstadoTicket() : ticketDB.getEstadoTicket());
                                ticketDB.setCodDevolucion(datosNuevos.getCodDevolucion() != null ? datosNuevos.getCodDevolucion() : ticketDB.getCodDevolucion());
                                if (datosNuevos.getNroTicket() != null) ticketDB.setNroTicket(datosNuevos.getNroTicket());
                                if (datosNuevos.getImporte() != null) ticketDB.setImporte(datosNuevos.getImporte());
                                ticketDB.setGestionado(true);
                            });
                    }
                }
                
                existente.setCompletado(false); 
                return ResponseEntity.ok(reclamoService.guardar(existente));
            }).orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error interno: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstadoCompletado(
            @PathVariable String id, 
            @RequestParam boolean estado) {
        try {
            return reclamoService.obtenerPorId(id).map(existente -> {
                existente.setCompletado(estado);
                return ResponseEntity.ok(reclamoService.guardar(existente));
            }).orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al cambiar estado: " + e.getMessage());
        }
    }
}
package com.example.bn.repository;

import com.example.bn.model.Reclamo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReclamoRepository extends JpaRepository<Reclamo, String> {
    
    // 1. Buscar todos con paginación (El orden se define en el Service o URL)
    Page<Reclamo> findAll(Pageable pageable);

    // 2. Encontrar pendientes con paginación
    Page<Reclamo> findByCompletadoFalse(Pageable pageable);

    // 3. Encontrar completados con paginación (útil para tu filtro)
    Page<Reclamo> findByCompletadoTrue(Pageable pageable);
    
    // 4. Buscar por documento con paginación
    Page<Reclamo> findByDocumentoCliente(String documentoCliente, Pageable pageable);

    List<Reclamo> findAllByOrderByCreatedAtDesc();
}
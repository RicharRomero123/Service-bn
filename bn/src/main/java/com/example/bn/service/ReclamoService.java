package com.example.bn.service;

import com.example.bn.model.Reclamo;
import com.example.bn.repository.ReclamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ReclamoService {

    @Autowired
    private ReclamoRepository reclamoRepository;

    // 1. Obtener todos los reclamos con PAGINACIÓN y ORDENADOS por más recientes
    public Page<Reclamo> listarTodosPaginados(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return reclamoRepository.findAll(pageable);
    }

    // 2. Obtener solo PENDIENTES con PAGINACIÓN y ORDENADOS por más recientes
    public Page<Reclamo> listarPendientesPaginados(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return reclamoRepository.findByCompletadoFalse(pageable);
    }

    // 3. Método flexible para filtrar por estado (opcional)
    public Page<Reclamo> listarPorEstado(int page, int size, Boolean completado) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        if (completado != null) {
            return completado ? 
                reclamoRepository.findByCompletadoTrue(pageable) : 
                reclamoRepository.findByCompletadoFalse(pageable);
        }
        return reclamoRepository.findAll(pageable);
    }

    // Mantener los métodos básicos que ya tenías
    public List<Reclamo> listarTodos() {
        return reclamoRepository.findAllByOrderByCreatedAtDesc();
    }

    public Optional<Reclamo> obtenerPorId(String id) {
        return reclamoRepository.findById(id);
    }

    public Reclamo guardar(Reclamo reclamo) {
        return reclamoRepository.save(reclamo);
    }

    public void eliminar(String id) {
        reclamoRepository.deleteById(id);
    }
}
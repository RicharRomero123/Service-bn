package com.example.bn.repository;

import com.example.bn.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    
    // Buscar usuario por su nombre de usuario (para el login)
    Optional<Usuario> findByUsuario(String usuario);
}
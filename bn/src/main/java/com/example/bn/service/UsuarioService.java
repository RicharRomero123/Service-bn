package com.example.bn.service;

import com.example.bn.model.Usuario;
import com.example.bn.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorUsername(String username) {
        return usuarioRepository.findByUsuario(username);
    }

    public Usuario registrar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    public boolean cambiarPassword(String username, String nuevoPassword) {
    return usuarioRepository.findByUsuario(username).map(usuario -> {
        usuario.setPassword(nuevoPassword);
        usuarioRepository.save(usuario);
        return true;
    }).orElse(false);
}
}
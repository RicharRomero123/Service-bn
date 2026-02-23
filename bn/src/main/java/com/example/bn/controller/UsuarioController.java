package com.example.bn.controller;

import com.example.bn.model.Usuario;
import com.example.bn.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> listar() {
        return usuarioService.listarUsuarios();
    }

    // 1. REGISTRO
    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody Usuario usuario) {
        // Validar si el nombre de usuario ya existe
        if (usuarioService.buscarPorUsername(usuario.getUsuario()).isPresent()) {
            return ResponseEntity.badRequest().body("El nombre de usuario '" + usuario.getUsuario() + "' ya está en uso.");
        }
        return ResponseEntity.ok(usuarioService.registrar(usuario));
    }

    // 2. LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario loginReq) {
        return usuarioService.buscarPorUsername(loginReq.getUsuario())
            .map(user -> {
                // Verificamos la contraseña (texto plano por ahora)
                if (user.getPassword().equals(loginReq.getPassword())) {
                    return ResponseEntity.ok(user); // Login exitoso
                }
                return ResponseEntity.status(401).body("Contraseña incorrecta");
            })
            .orElse(ResponseEntity.status(404).body("Usuario no encontrado"));
    }
    @PatchMapping("/cambiar-password")
public ResponseEntity<?> cambiarPassword(@RequestBody Usuario req) {
    // Usamos el campo 'usuario' y 'password' que vienen en el body
    boolean actualizado = usuarioService.cambiarPassword(req.getUsuario(), req.getPassword());
    
    if (actualizado) {
        return ResponseEntity.ok("Contraseña actualizada correctamente para el usuario: " + req.getUsuario());
    } else {
        return ResponseEntity.status(404).body("No se pudo encontrar el usuario: " + req.getUsuario());
    }
}

@GetMapping("/perfil/{username}")
public ResponseEntity<?> verPerfil(@PathVariable String username) {
    return usuarioService.buscarPorUsername(username)
            .map(usuario -> ResponseEntity.ok((Object) usuario))
            .orElse(ResponseEntity.status(404).body("Error: El usuario '" + username + "' no existe en el sistema."));
}

}
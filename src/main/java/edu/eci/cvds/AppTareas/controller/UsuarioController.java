package edu.eci.cvds.AppTareas.controller;

import edu.eci.cvds.AppTareas.model.Tarea;
import edu.eci.cvds.AppTareas.model.Usuario;
import edu.eci.cvds.AppTareas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<String> crear(@RequestBody Usuario usuario) {
        try {
            Usuario nuevoUsuario = usuarioService.crearUsuario(usuario);
            return new ResponseEntity<>("Usuario creado: " + nuevoUsuario.getNombre(), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<Usuario> consultarUsuarios() {
        return usuarioService.obtenerUsuarios();
    }

    @GetMapping("/{usuarioId}")
    public Optional<Usuario> consultarUsuario(@PathVariable String usuarioId) {
        return usuarioService.obtenerUsuario(usuarioId);
    }

    @DeleteMapping("/{usuarioId}")
    public void eliminarUsuario(@PathVariable String usuarioId) {
        usuarioService.eliminarUsuario(usuarioId);
    }

    @GetMapping("/verificar/{nombre}/{contrasena}")
    public boolean verificarPass(@PathVariable String nombre, @PathVariable String contrasena) {

        Usuario usuario = usuarioService.encontrarUsuario(nombre);
        if (usuario != null) {
            String contrasenaAlmacenada = usuario.getContrasena();
            boolean match = passwordEncoder.matches(contrasena, contrasenaAlmacenada);
            return match;
        }

        System.out.println("Usuario no encontrado: " + nombre);
        return false;
    }

    @GetMapping("/tareas/{nombre}")
    public List<Tarea> tareasUsuario(@PathVariable String nombre) {
        return usuarioService.obtenerTareasDeUsuario(nombre);
    }
}
package edu.eci.cvds.AppTareas.controller;

import edu.eci.cvds.AppTareas.model.Tarea;
import edu.eci.cvds.AppTareas.model.usuario;
import edu.eci.cvds.AppTareas.service.usuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class usuarioController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    private usuarioService usuarioService;

    public usuarioController(usuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<String> crear(@RequestBody usuario usuario) {
        try {
            usuario nuevoUsuario = usuarioService.crearUsuario(usuario);
            return new ResponseEntity<>("Usuario creado: " + nuevoUsuario.getNombre(), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<usuario> consultarUsuarios() {
        return usuarioService.obtenerUsuarios();
    }

    @GetMapping("/{usuarioId}")
    public Optional<usuario> consultarUsuario(@PathVariable String usuarioId) {
        return usuarioService.obtenerUsuario(usuarioId);
    }

    @DeleteMapping("/{usuarioId}")
    public void eliminarUsuario(@PathVariable String usuarioId) {
        usuarioService.eliminarUsuario(usuarioId);
    }

    @GetMapping("/verificar/{nombre}/{contraseña}")
    public boolean verificarPass(@PathVariable String nombre, @PathVariable String contraseña) {

        usuario usuario = usuarioService.encontrarUsuario(nombre);
        if (usuario != null) {
            String contrasenaAlmacenada = usuario.getContraseña();
            boolean match = passwordEncoder.matches(contraseña, contrasenaAlmacenada);
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
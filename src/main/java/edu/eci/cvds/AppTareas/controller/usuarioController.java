package edu.eci.cvds.AppTareas.controller;


import edu.eci.cvds.AppTareas.model.Tarea;
import edu.eci.cvds.AppTareas.model.usuario;
import edu.eci.cvds.AppTareas.service.usuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class usuarioController {


    private usuarioService usuarioService; //Inyectado por constructor

    public usuarioController(usuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public usuario crear(@RequestBody usuario usuario) {
        return usuarioService.crearUsuario(usuario);
    }

    @GetMapping
    public List<usuario> consultarUsuarios() {
        return usuarioService.obtenerUsuarios();
    }

    @GetMapping("/{usuariosId}")
    public Optional<usuario> consultarusuario(@PathVariable String usuarioID) {
        return usuarioService.obtenerUsuario(usuarioID);
    }

    @DeleteMapping("/{usuarioId}")
    public void eliminarTarea(@PathVariable String usuarioID) {
        usuarioService.eliminarUsuario(usuarioID);
    }

    @GetMapping("/{nombre}/{contraseña}")
    public boolean verificarpass(@PathVariable String nombre, @PathVariable String contraseña){
        return usuarioService.verifacarpass(nombre,contraseña);
    }

    @GetMapping("/tareas/{nombre}")
    public List<Tarea> tareasUsuario(@PathVariable String nombre){
        return usuarioService.obtenerTareasDeUsuario(nombre);
    }



}

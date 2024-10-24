package edu.eci.cvds.AppTareas.controller;

import java.util.List;

import edu.eci.cvds.AppTareas.model.Tarea;
import edu.eci.cvds.AppTareas.service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tareas")
public class TareaController {

    private final TareaService tareaService; //Inyectado por constructor

    public TareaController(TareaService tareaService){
        this.tareaService = tareaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tarea crearTarea(@RequestBody Tarea tarea) {
        return tareaService.crear(tarea);
    }

    @GetMapping
    public List<Tarea> consultarTareas() {
        return tareaService.obtenerTareas();
    }

    @GetMapping("/{tareaId}")
    public Tarea consultarTarea(@PathVariable String tareaId) {
        return tareaService.obtenerTarea(tareaId);
    }

    @GetMapping("cambio/{tareaId}")
    public boolean cambiarEstado(@PathVariable String tareaId) {
        return tareaService.cambiarEstado(tareaId);
    }

    @DeleteMapping("/{tareaId}")
    public void eliminarTarea(@PathVariable String tareaId) {
        tareaService.eliminarTarea(tareaId);
    }

    @PutMapping("/{tareaId}")
    public void actualizarTarea(@PathVariable String tareaId, @RequestBody Tarea nuevaTarea) {
        tareaService.actualizarTarea(tareaId,nuevaTarea);
    }
    /*
    @PostMapping("/generarAleatorias")
    @ResponseStatus(HttpStatus.CREATED)
    public void generarTareasAleatorias() {
        tareaService.generarTareasAleatorias();
    }

    @GetMapping("/obtenerTareas")
    public ResponseEntity<List<Tarea>> obtenerTareas() {
        List<Tarea> tareas = tareaService.obtenerTareas(); // Obtiene la lista de tareas
        if (tareas.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Si no hay tareas, devuelve 204 No Content
        }
        return new ResponseEntity<>(tareas, HttpStatus.OK); // Devuelve la lista de tareas con 200 OK
    }*/
}
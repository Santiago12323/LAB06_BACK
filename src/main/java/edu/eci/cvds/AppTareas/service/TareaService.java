package edu.eci.cvds.AppTareas.service;

import edu.eci.cvds.AppTareas.model.Tarea;
import edu.eci.cvds.AppTareas.repository.TareaPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TareaService {

    private final TareaPersistence tareaPersistence;

    @Autowired
    public TareaService(TareaPersistence tareaPersistence) {
        this.tareaPersistence = tareaPersistence;
    }

    public Tarea crear(Tarea tarea) {
        return tareaPersistence.save(tarea);
    }

    public List<Tarea> obtenerTareas() {
        return tareaPersistence.findAll();
    }

    public Tarea obtenerTarea(String tareaId) {
        Optional<Tarea> tarea = tareaPersistence.findById(tareaId);
        return tarea.orElseThrow(() -> new IllegalArgumentException("Tarea con ID " + tareaId + " no existe"));
    }

    public void eliminarTarea(String tareaId) {
        // Verifica si el ID de la tarea es nulo o vacío
        if (tareaId == null || tareaId.isEmpty()) {
            throw new IllegalArgumentException("El ID de la tarea no puede ser nulo o vacío");
        }

        // Verifica si la tarea existe antes de intentar eliminarla
        Optional<Tarea> tarea = tareaPersistence.findById(tareaId);
        if (tarea.isEmpty()) {
            throw new IllegalArgumentException("No se puede eliminar la tarea. La tarea con ID " + tareaId + " no existe.");
        }

        // Procedemos a eliminar la tarea
        tareaPersistence.deleteById(tareaId);
    }


    public void actualizarTarea(String tareaId, Tarea nuevaTarea) {
        Tarea tarea = obtenerTarea(tareaId);
        tarea.setNombre(nuevaTarea.getNombre());
        tarea.setDescripcion(nuevaTarea.getDescripcion());
        tarea.setEstado(nuevaTarea.getEstado());
        tareaPersistence.save(tarea);
    }

    public boolean cambiarEstado(String tareaId) {
        Tarea tarea = obtenerTarea(tareaId);
        tarea.setEstado(!tarea.getEstado());
        tareaPersistence.save(tarea);
        return true;
    }
}
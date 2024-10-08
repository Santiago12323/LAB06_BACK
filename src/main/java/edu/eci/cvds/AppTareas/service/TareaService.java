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
        String id = UUID.randomUUID().toString();
        tarea.setId(id);
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

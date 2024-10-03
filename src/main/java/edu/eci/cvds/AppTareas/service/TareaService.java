package edu.eci.cvds.AppTareas.service;

import edu.eci.cvds.AppTareas.model.Tarea;
import edu.eci.cvds.AppTareas.repository.TareaPersistence;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TareaService {

    private final TareaPersistence tareaPersistence;

    public TareaService(@Qualifier("mongoDB") TareaPersistence tareaPersistence) {
        this.tareaPersistence = tareaPersistence;
    }

    public Tarea crear(Tarea tarea) {
        String id = UUID.randomUUID().toString();
        tarea.setId(id);
        return tareaPersistence.guardar(tarea);
    }

    public List<Tarea> obtenerTareas() {
        return tareaPersistence.obtenerTodas();
    }

    public Tarea obtenerTarea(String tareaId) {
        Optional<Tarea> tarea = tareaPersistence.obtenerPorId(tareaId);
        return tarea.orElseThrow(() -> new IllegalArgumentException("Tarea con ID " + tareaId + " no existe"));
    }

    public void eliminarTarea(String tareaId) {
        tareaPersistence.eliminar(tareaId);
    }

    public void actualizarTarea(String tareaId, Tarea nuevaTarea) {
        Tarea tarea = obtenerTarea(tareaId);
        tarea.setNombre(nuevaTarea.getNombre());
        tarea.setDescripcion(nuevaTarea.getDescripcion());
        tarea.setEstado(nuevaTarea.getEstado());
        tareaPersistence.guardar(tarea);
    }

    public boolean cambiarEstado(String tareaId) {
        Tarea tarea = obtenerTarea(tareaId);
        tarea.setEstado(!tarea.getEstado());
        tareaPersistence.guardar(tarea);
        return true;
    }
}

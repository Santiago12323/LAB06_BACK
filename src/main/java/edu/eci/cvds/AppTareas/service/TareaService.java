package edu.eci.cvds.AppTareas.service;

import edu.eci.cvds.AppTareas.repository.TareaRepository;
import edu.eci.cvds.AppTareas.model.Tarea;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TareaService {
    private final TareaRepository tareaRepository;

    public Tarea crear(Tarea tarea) {
        String id = UUID.randomUUID().toString();
        tarea.setId(id);
        tareaRepository.save(tarea);
        return tarea;
    }

    public List<Tarea> obtenerTareas() {
        return tareaRepository.findAll();
    }

    public Tarea obtenerTarea(String tareaId) {
        return tareaRepository.findById(tareaId).orElse(null);
    }

    public void eliminarTarea(String tareaId){
        tareaRepository.deleteById(tareaId);
    }

    public void actualizarTarea(String tareaId,Tarea nuevaTarea){
        Tarea tarea = obtenerTarea(tareaId);
        if (tarea != null) {
            tarea.setNombre(nuevaTarea.getNombre());
            tarea.setDescripcion(nuevaTarea.getDescripcion());
            tarea.setEstado(nuevaTarea.getEstado());
            tareaRepository.save(tarea);
        } else {
            throw new IllegalArgumentException("Tarea con ID " + tareaId + " no existe");
        }
    }

    public boolean cambiarEstado(String tareaId) {
        Tarea tarea = obtenerTarea(tareaId);
        if (tarea != null) {
            tarea.setEstado(!tarea.getEstado());
            tareaRepository.save(tarea);
            return true;
        }
        return false;
    }
}

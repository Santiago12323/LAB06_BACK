package edu.eci.cvds.AppTareas.repository;

import edu.eci.cvds.AppTareas.model.Tarea;

import java.util.List;
import java.util.Optional;

public interface TareaPersistence {
    Tarea guardar(Tarea tarea);
    Optional<Tarea> obtenerPorId(String id);
    List<Tarea> obtenerTodas();
    void eliminar(String id);
}

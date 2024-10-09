package edu.eci.cvds.AppTareas.repository;

import edu.eci.cvds.AppTareas.model.Tarea;

import java.util.List;
import java.util.Optional;

public interface TareaPersistence {
    Tarea save(Tarea tarea);
    Optional<Tarea> findById(String id);
    List<Tarea> findAll();
    void deleteById(String id);
}
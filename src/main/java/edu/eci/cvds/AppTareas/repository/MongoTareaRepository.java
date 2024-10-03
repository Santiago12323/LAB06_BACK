package edu.eci.cvds.AppTareas.repository;

import edu.eci.cvds.AppTareas.model.Tarea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository("mongoDB")
public class MongoTareaRepository implements TareaPersistence {

    @Autowired
    private TareaRepository tareaRepository;  // Este repositorio ya está configurado para MongoDB

    @Override
    public Tarea guardar(Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    @Override
    public Optional<Tarea> obtenerPorId(String id) {
        return tareaRepository.findById(id);
    }

    @Override
    public List<Tarea> obtenerTodas() {
        return tareaRepository.findAll();
    }

    @Override
    public void eliminar(String id) {
        tareaRepository.deleteById(id);
    }
}

package edu.eci.cvds.AppTareas.repository;

import edu.eci.cvds.AppTareas.model.Tarea;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TareaRepository extends MongoRepository<Tarea, String> {
}

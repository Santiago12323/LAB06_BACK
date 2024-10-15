package edu.eci.cvds.AppTareas.repository.mongo;

import edu.eci.cvds.AppTareas.model.Tarea;
import edu.eci.cvds.AppTareas.repository.TareaPersistence;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository("mongoDB")
public interface MongoTareaRepository extends MongoRepository<Tarea, String>, TareaPersistence {
}
package edu.eci.cvds.AppTareas.repository.mongo;

import edu.eci.cvds.AppTareas.model.Tarea;
import edu.eci.cvds.AppTareas.model.usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("mongoDB")
public interface MongoUsuarioRepository extends MongoRepository<usuario, String> {
    usuario findByNombre(String nombre);
}


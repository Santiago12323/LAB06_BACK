package edu.eci.cvds.AppTareas.repository.mongo;

import edu.eci.cvds.AppTareas.model.Tarea;
import edu.eci.cvds.AppTareas.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository("mongoDB")
public interface MongoUsuarioRepository extends MongoRepository<Usuario, String> {
    Usuario findByNombre(String nombre);
}


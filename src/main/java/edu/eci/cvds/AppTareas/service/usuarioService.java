package edu.eci.cvds.AppTareas.service;
import edu.eci.cvds.AppTareas.model.Tarea;
import edu.eci.cvds.AppTareas.repository.mongo.MongoUsuarioRepository;
import edu.eci.cvds.AppTareas.repository.mongo.MongoTareaRepository;
import edu.eci.cvds.AppTareas.model.usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class usuarioService {

    private final MongoUsuarioRepository MongoUsuarioRepository;
    private final MongoTareaRepository MongoTareaRepository;


    @Autowired
    public usuarioService(MongoUsuarioRepository MongoUsuarioRepository, MongoTareaRepository MongoTareaRepository) {
        this.MongoUsuarioRepository = MongoUsuarioRepository;
        this.MongoTareaRepository = MongoTareaRepository;
    }

    public usuario crearUsuario(usuario usuario) {
        return MongoUsuarioRepository.save(usuario);
    }

    public List<usuario> obtenerUsuarios() {
        return MongoUsuarioRepository.findAll();
    }

    public Optional<usuario> obtenerUsuario(String id) {
        return MongoUsuarioRepository.findById(id);
    }

    public void eliminarUsuario(String Id) {
        if (Id == null || Id.isEmpty()) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo o vacío");
        }

        // Verifica si la tarea existe antes de intentar eliminarla
        Optional<usuario> usuario = MongoUsuarioRepository.findById(Id);
        if (usuario.isEmpty()) {
            throw new IllegalArgumentException("No se puede eliminar el usuario. el usuario con ID " + Id + " no existe.");
        }

        // Procedemos a eliminar la tarea
        MongoUsuarioRepository.deleteById(Id);
    }

    public boolean verifacarpass(String nombre,String pass){
        usuario usuario = MongoUsuarioRepository.findByNombre(nombre);
        if (usuario != null) {
            return pass.equals(usuario.getContraseña());
        }
        return false;
    }

    public List<Tarea> obtenerTareasDeUsuario(String nombre) {
        return MongoTareaRepository.findByUsuarioNombre(nombre);
    }



}

package edu.eci.cvds.AppTareas.service;

import edu.eci.cvds.AppTareas.model.Tarea;
import edu.eci.cvds.AppTareas.model.Usuario;
import edu.eci.cvds.AppTareas.repository.mongo.MongoTareaRepository;
import edu.eci.cvds.AppTareas.repository.mongo.MongoUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final MongoUsuarioRepository MongoUsuarioRepository;
    private final MongoTareaRepository MongoTareaRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UsuarioService(MongoUsuarioRepository MongoUsuarioRepository, MongoTareaRepository MongoTareaRepository) {
        this.MongoUsuarioRepository = MongoUsuarioRepository;
        this.MongoTareaRepository = MongoTareaRepository;
    }

    public Usuario crearUsuario(Usuario usuario) {
        if (MongoUsuarioRepository.findByNombre(usuario.getNombre()) != null){
            throw new IllegalArgumentException("El nombre" + usuario.getNombre() + "ya se encuentra en uso.");
        }
        return MongoUsuarioRepository.save(usuario);
    }


    public List<Usuario> obtenerUsuarios() {
        return MongoUsuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerUsuario(String id) {
        return MongoUsuarioRepository.findById(id);
    }

    public void eliminarUsuario(String Id) {
        if (Id == null || Id.isEmpty()) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo o vacío");
        }

        Optional<Usuario> usuario = MongoUsuarioRepository.findById(Id);
        if (usuario.isEmpty()) {
            throw new IllegalArgumentException("No se puede eliminar el usuario. El usuario con ID " + Id + " no existe.");
        }

        MongoUsuarioRepository.deleteById(Id);
    }

    public boolean verificarPass(String nombre, String pass) {
        Usuario usuario = MongoUsuarioRepository.findByNombre(nombre);
        if (usuario != null) {
            return passwordEncoder.matches(pass, usuario.getContrasena());
        }
        return false;
    }

    public List<Tarea> obtenerTareasDeUsuario(String nombre) {
        return MongoTareaRepository.findByUsuarioNombre(nombre);
    }

    public Usuario encontrarUsuario(String nombre){
        return MongoUsuarioRepository.findByNombre(nombre);
    }
}
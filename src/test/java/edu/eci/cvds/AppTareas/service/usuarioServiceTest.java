package edu.eci.cvds.AppTareas.service;

import edu.eci.cvds.AppTareas.model.Tarea;
import edu.eci.cvds.AppTareas.model.usuario;
import edu.eci.cvds.AppTareas.repository.mongo.MongoUsuarioRepository;
import edu.eci.cvds.AppTareas.repository.mongo.MongoTareaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class usuarioServiceTest {

    private MongoUsuarioRepository mockUsuarioRepository;
    private MongoTareaRepository mockTareaRepository;
    private usuarioService usuarioService;

    @BeforeEach
    void setUp() {
        mockUsuarioRepository = Mockito.mock(MongoUsuarioRepository.class);
        mockTareaRepository = Mockito.mock(MongoTareaRepository.class);
        usuarioService = new usuarioService(mockUsuarioRepository, mockTareaRepository);
    }

    @Test
    void testCrearUsuario() {
        usuario newUser = new usuario("1", "testUser", "password");
        when(mockUsuarioRepository.save(any(usuario.class))).thenReturn(newUser);

        usuario createdUser = usuarioService.crearUsuario(newUser);

        assertEquals(newUser, createdUser);
        verify(mockUsuarioRepository).save(newUser);
    }

    @Test
    void testObtenerUsuarios() {
        usuario user1 = new usuario("1", "user1", "pass1");
        usuario user2 = new usuario("2", "user2", "pass2");
        when(mockUsuarioRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<usuario> usuarios = usuarioService.obtenerUsuarios();

        assertEquals(2, usuarios.size());
        assertTrue(usuarios.contains(user1));
        assertTrue(usuarios.contains(user2));
    }

    @Test
    void testObtenerUsuario() {
        usuario user = new usuario("1", "user", "pass");
        when(mockUsuarioRepository.findById("1")).thenReturn(Optional.of(user));

        Optional<usuario> foundUser = usuarioService.obtenerUsuario("1");

        assertTrue(foundUser.isPresent());
        assertEquals(user, foundUser.get());
    }

    @Test
    void testEliminarUsuario() {
        usuario user = new usuario("1", "user", "pass");
        when(mockUsuarioRepository.findById("1")).thenReturn(Optional.of(user));

        usuarioService.eliminarUsuario("1");

        verify(mockUsuarioRepository).deleteById("1");
    }

    @Test
    void testEliminarUsuario_NoUserFound() {
        when(mockUsuarioRepository.findById("1")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            usuarioService.eliminarUsuario("1");
        });

        assertEquals("No se puede eliminar el usuario. el usuario con ID 1 no existe.", exception.getMessage());
    }

    @Test
    void testVerificarPass() {
        usuario user = new usuario("1", "testUser", "password");
        when(mockUsuarioRepository.findByNombre("testUser")).thenReturn(user);

        boolean result = usuarioService.verifacarpass("testUser", "password");

        assertFalse(result);
    }

    @Test
    void testObtenerTareasDeUsuario() {
        Tarea tarea1 = new Tarea("1", "Tarea 1", "Descripción 1", true, "coronado" , 1, "Baja", 2.0);
        Tarea tarea2 = new Tarea("2", "Tarea 2", "Descripción 2", false,"coronado", 2, "Media", 3.5);
        when(mockTareaRepository.findByUsuarioNombre("coronado")).thenReturn(Arrays.asList(tarea1, tarea2));

        List<Tarea> tareas = usuarioService.obtenerTareasDeUsuario("coronado");

        assertEquals(2, tareas.size());
        assertTrue(tareas.contains(tarea1));
        assertTrue(tareas.contains(tarea2));
    }
}
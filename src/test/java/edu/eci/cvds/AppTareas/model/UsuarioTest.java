package edu.eci.cvds.AppTareas.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    private usuario usuario; // Mantener 'usuario' con minúscula

    @BeforeEach
    void setUp() {
        usuario = new usuario("contraseña123", "Juan", "1"); // Asegúrate de que el constructor sea correcto
    }

    @Test
    void testAgregarYObtenerTareas() {
        // Crea una nueva tarea
        Tarea tarea = new Tarea("1", "Tarea 1", "Descripción de la tarea", false, "Juan", 1, "Baja", 2.0);

        // Crea una lista de tareas y añade la tarea
        List<Tarea> tareas = new ArrayList<>();
        tareas.add(tarea);
        usuario.setTareas(tareas); // Asumiendo que setTareas permite establecer la lista de tareas

        // Verifica que la lista de tareas contiene la tarea agregada
        assertEquals(1, usuario.getTareas().size(), "El usuario debería tener una tarea.");
        assertTrue(usuario.getTareas().contains(tarea), "La tarea debería estar en la lista de tareas del usuario.");
    }


    @Test
    void testGetId() {
        assertEquals("1", usuario.getId());
    }

    @Test
    void testGetNombre() {
        assertEquals("Juan", usuario.getNombre());
    }



    @Test
    void testSetId() {
        usuario.setId("2");
        assertEquals("2", usuario.getId());
    }

    @Test
    void testSetNombre() {
        usuario.setNombre("Pedro");
        assertEquals("Pedro", usuario.getNombre());
    }

    @Test
    void testSetContraseña() {
        usuario.setContraseña("nuevaContraseña");
        assertEquals("nuevaContraseña", usuario.getContraseña());
    }

}

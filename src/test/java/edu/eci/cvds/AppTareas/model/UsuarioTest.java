package edu.eci.cvds.AppTareas.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    private usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new usuario("contraseña123", "Juan", "1");
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
    void testGetContraseña() {
        assertEquals("contraseña123", usuario.getContraseña());
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

    @Test
    void testTareas() {
        List<Tarea> tareas = new ArrayList<>();
        usuario.setTareas(tareas);
        assertEquals(tareas, usuario.getTareas());
        }
}
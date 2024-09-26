package edu.eci.cvds.AppTareas.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TareaTest {

    @Test
    void testSettersAndGetters() {
        Tarea tarea = new Tarea();

        tarea.setNombre("Tarea 1");
        tarea.setDescripcion("Descripción de la tarea 1");
        tarea.setEstado(false);

        assertEquals("Tarea 1", tarea.getNombre());
        assertEquals("Descripción de la tarea 1", tarea.getDescripcion());
        assertFalse(tarea.getEstado());
    }
}
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
        tarea.setPrioridad(4);
        tarea.setDificultad("Alto");
        tarea.setTiempoPromedio(10);

        assertEquals("Tarea 1", tarea.getNombre());
        assertEquals("Descripción de la tarea 1", tarea.getDescripcion());
        assertFalse(tarea.getEstado());
        assertEquals(4,tarea.getPrioridad());
        assertEquals("Alto", tarea.getDificultad());
        assertEquals(10,tarea.getTiempoPromedio());
    }
}
package edu.eci.cvds.AppTareas.controller;

import edu.eci.cvds.AppTareas.model.Tarea;
import edu.eci.cvds.AppTareas.service.TareaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;


import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TareaController.class)
class TareaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TareaService tareaService;

    private Tarea tarea;

    @BeforeEach
    void setUp() {
        tarea = new Tarea();
        tarea.setId("1");
        tarea.setNombre("Tarea 1");
        tarea.setDescripcion("Descripción de la tarea 1");
        tarea.setEstado(false);
    }

    @Test
    void testCrearTarea() {
        // Prueba simple para verificar que se puede crear una tarea y asignar sus atributos.
        Tarea nuevaTarea = new Tarea(UUID.randomUUID().toString(), "Nueva Tarea", "Descripción de prueba", false, null, 4, "Alto", 10);

        assertEquals("Nueva Tarea", nuevaTarea.getNombre());
        assertEquals("Descripción de prueba", nuevaTarea.getDescripcion());
        assertFalse(nuevaTarea.getEstado());
    }

    @Test
    void testActualizarTarea() {
        tarea.setNombre("Tarea Actualizada");
        tarea.setDescripcion("Descripción Actualizada");

        assertEquals("Tarea Actualizada", tarea.getNombre());
        assertEquals("Descripción Actualizada", tarea.getDescripcion());
    }

    @Test
    @WithMockUser(roles = "USER")
    void testEliminarTarea() throws Exception {
        mockMvc.perform(delete("/tareas/1")
                        .with(csrf()))
                .andExpect(status().isOk());
    }


}

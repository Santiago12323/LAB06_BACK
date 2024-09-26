package edu.eci.cvds.AppTareas.controller;

import edu.eci.cvds.AppTareas.model.Tarea;
import edu.eci.cvds.AppTareas.service.TareaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(TareaController.class)
class TareaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TareaService tareaService;

    private Tarea tarea;

    @BeforeEach
    void setUp() {
        tarea = new Tarea(UUID.randomUUID().toString(), "Tarea 1", "Descripción 1", false);
    }

    @Test
    void testCrearTarea() throws Exception {
        when(tareaService.crear(any(Tarea.class))).thenReturn(tarea);

        mockMvc.perform(post("/tareas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"nombre\": \"Tarea 1\", \"descripcion\": \"Descripción 1\", \"estado\": false }"))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(tareaService, times(1)).crear(any(Tarea.class));
    }

    @Test
    void testConsultarTareas() throws Exception {
        when(tareaService.obtenerTareas()).thenReturn(List.of(tarea));

        mockMvc.perform(get("/tareas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(tareaService, times(1)).obtenerTareas();
    }

    @Test
    void testConsultarTareaPorId() throws Exception {
        when(tareaService.obtenerTarea(anyString())).thenReturn(tarea);

        mockMvc.perform(get("/tareas/" + tarea.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(tareaService, times(1)).obtenerTarea(tarea.getId());
    }

    @Test
    void testCambiarEstado() throws Exception {
        when(tareaService.cambiarEstado(anyString())).thenReturn(true);

        mockMvc.perform(get("/tareas/cambio/" + tarea.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(tareaService, times(1)).cambiarEstado(tarea.getId());
    }

    @Test
    void testEliminarTarea() throws Exception {
        doNothing().when(tareaService).eliminarTarea(anyString());

        mockMvc.perform(delete("/tareas/" + tarea.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(tareaService, times(1)).eliminarTarea(tarea.getId());
    }

    @Test
    void testActualizarTarea() throws Exception {
        Tarea nuevaTarea = new Tarea(tarea.getId(), "Tarea Actualizada", "Descripción Actualizada", true);

        doNothing().when(tareaService).actualizarTarea(anyString(), any(Tarea.class));

        mockMvc.perform(put("/tareas/" + tarea.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"nombre\": \"Tarea Actualizada\", \"descripcion\": \"Descripción Actualizada\", \"estado\": true }"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(tareaService, times(1)).actualizarTarea(eq(tarea.getId()), any(Tarea.class));
    }
}

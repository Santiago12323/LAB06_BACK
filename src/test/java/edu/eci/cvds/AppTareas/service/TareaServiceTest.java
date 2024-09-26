package edu.eci.cvds.AppTareas.service;

import edu.eci.cvds.AppTareas.model.Tarea;
import edu.eci.cvds.AppTareas.repository.TareaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TareaServiceTest {

    private TareaRepository tareaRepository;
    private TareaService tareaService;

    @BeforeEach
    void setUp() {
        tareaRepository = Mockito.mock(TareaRepository.class);
        tareaService = new TareaService(tareaRepository);
    }

    @Test
    void testCrearTarea() {
        Tarea tarea = new Tarea(null, "Tarea 1", "Descripción 1", false);

        when(tareaRepository.save(any(Tarea.class))).thenReturn(tarea);

        Tarea tareaCreada = tareaService.crear(tarea);

        assertNotNull(tareaCreada.getId());
        assertEquals("Tarea 1", tareaCreada.getNombre());
        verify(tareaRepository, times(1)).save(tarea);
    }

    @Test
    void testObtenerTarea() {
        String tareaId = UUID.randomUUID().toString();
        Tarea tarea = new Tarea(tareaId, "Tarea 1", "Descripción 1", false);

        when(tareaRepository.findById(tareaId)).thenReturn(Optional.of(tarea));

        Tarea tareaObtenida = tareaService.obtenerTarea(tareaId);

        assertNotNull(tareaObtenida);
        assertEquals(tareaId, tareaObtenida.getId());
        verify(tareaRepository, times(1)).findById(tareaId);
    }

    @Test
    void testEliminarTarea() {
        String tareaId = UUID.randomUUID().toString();

        doNothing().when(tareaRepository).deleteById(tareaId);

        tareaService.eliminarTarea(tareaId);

        verify(tareaRepository, times(1)).deleteById(tareaId);
    }

    @Test
    void testCambiarEstado() {
        String tareaId = UUID.randomUUID().toString();
        Tarea tarea = new Tarea(tareaId, "Tarea 1", "Descripción 1", false);

        when(tareaRepository.findById(tareaId)).thenReturn(Optional.of(tarea));
        when(tareaRepository.save(any(Tarea.class))).thenReturn(tarea);

        boolean resultado = tareaService.cambiarEstado(tareaId);

        assertTrue(resultado);
        assertTrue(tarea.getEstado());
        verify(tareaRepository, times(1)).save(tarea);
    }

    @Test
    void testCambiarEstadoTareaNoExistente() {
        String tareaId = UUID.randomUUID().toString();

        // Simulamos que la tarea no existe devolviendo Optional.empty()
        when(tareaRepository.findById(tareaId)).thenReturn(Optional.empty());

        boolean resultado = tareaService.cambiarEstado(tareaId);

        assertFalse(resultado);  // Debe devolver falso porque la tarea no existe
        verify(tareaRepository, never()).save(any(Tarea.class));  // Verificamos que no se intenta guardar una tarea
    }

    @Test
    void testObtenerTareas() {
        Tarea tarea1 = new Tarea(UUID.randomUUID().toString(), "Tarea 1", "Descripción 1", false);
        Tarea tarea2 = new Tarea(UUID.randomUUID().toString(), "Tarea 2", "Descripción 2", true);

        when(tareaRepository.findAll()).thenReturn(List.of(tarea1, tarea2));

        List<Tarea> tareas = tareaService.obtenerTareas();

        assertEquals(2, tareas.size());
        verify(tareaRepository, times(1)).findAll();
    }

    @Test
    void testActualizarTarea() {
        String tareaId = UUID.randomUUID().toString();
        Tarea tareaExistente = new Tarea(tareaId, "Tarea 1", "Descripción 1", false);
        Tarea nuevaTarea = new Tarea(tareaId, "Tarea Actualizada", "Descripción Actualizada", true);

        when(tareaRepository.findById(tareaId)).thenReturn(Optional.of(tareaExistente));

        tareaService.actualizarTarea(tareaId, nuevaTarea);

        verify(tareaRepository, times(1)).findById(tareaId);
        verify(tareaRepository, times(1)).save(tareaExistente);

        assertEquals("Tarea Actualizada", tareaExistente.getNombre());
        assertEquals("Descripción Actualizada", tareaExistente.getDescripcion());
        assertTrue(tareaExistente.getEstado());
    }

    @Test
    void testActualizarTareaNoExistente() {
        String tareaIdInexistente = UUID.randomUUID().toString();
        Tarea nuevaTarea = new Tarea(tareaIdInexistente, "Nueva Tarea", "Nueva Descripción", true);

        when(tareaRepository.findById(tareaIdInexistente)).thenReturn(Optional.empty());

        // Espera que se lance una IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            tareaService.actualizarTarea(tareaIdInexistente, nuevaTarea);
        });

        verify(tareaRepository, never()).save(any(Tarea.class)); // Nunca debe intentar guardar la tarea.
    }
}

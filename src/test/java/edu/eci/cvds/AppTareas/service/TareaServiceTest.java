package edu.eci.cvds.AppTareas.service;

import edu.eci.cvds.AppTareas.model.Tarea;
import edu.eci.cvds.AppTareas.repository.TareaPersistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TareaServiceTest {

    @Mock
    private TareaPersistence tareaPersistence;

    @Spy
    private Random random = new Random(); // Usamos un Spy para controlar el Random

    @InjectMocks
    private TareaService tareaService;

    private Tarea tarea;

    @BeforeEach
    void setUp() {
        // Inicializamos los mocks y la clase de servicio
        MockitoAnnotations.openMocks(this);
        tarea = new Tarea();
        tarea.setId("1");
        tarea.setNombre("Tarea 1");
        tarea.setDescripcion("Descripción de la tarea 1");
        tarea.setEstado(false);
    }

    // PRUEBAS BÁSICAS DE FUNCIONAMIENTO

    @Test
    void testCrearTarea() {
        when(tareaPersistence.save(any(Tarea.class))).thenReturn(tarea);

        Tarea tareaCreada = tareaService.crear(tarea);

        assertNotNull(tareaCreada.getId());
        assertEquals(tarea.getNombre(), tareaCreada.getNombre());
        verify(tareaPersistence, times(1)).save(any(Tarea.class));
    }

    @Test
    void testObtenerTareas() {
        List<Tarea> tareas = new ArrayList<>();
        tareas.add(tarea);
        when(tareaPersistence.findAll()).thenReturn(tareas);

        List<Tarea> tareasObtenidas = tareaService.obtenerTareas();

        assertEquals(1, tareasObtenidas.size());
        assertEquals(tarea.getNombre(), tareasObtenidas.get(0).getNombre());
        verify(tareaPersistence, times(1)).findAll();
    }

    @Test
    void testObtenerTareaExistente() {
        when(tareaPersistence.findById(tarea.getId())).thenReturn(Optional.of(tarea));

        Tarea tareaObtenida = tareaService.obtenerTarea(tarea.getId());

        assertEquals(tarea.getId(), tareaObtenida.getId());
        verify(tareaPersistence, times(1)).findById(tarea.getId());
    }

    @Test
    void testObtenerTareaNoExistente() {
        when(tareaPersistence.findById(tarea.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            tareaService.obtenerTarea(tarea.getId());
        });

        assertEquals("Tarea con ID 1 no existe", exception.getMessage());
    }

    @Test
    void testEliminarTareaExistente() {
        when(tareaPersistence.findById(tarea.getId())).thenReturn(Optional.of(tarea));

        tareaService.eliminarTarea(tarea.getId());

        verify(tareaPersistence, times(1)).deleteById(tarea.getId());
    }

    @Test
    void testEliminarTareaNoExistente() {
        when(tareaPersistence.findById(tarea.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            tareaService.eliminarTarea(tarea.getId());
        });

        assertEquals("No se puede eliminar la tarea. La tarea con ID 1 no existe.", exception.getMessage());
    }

    @Test
    void testEliminarTareaConIdNulo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            tareaService.eliminarTarea(null);
        });

        assertEquals("El ID de la tarea no puede ser nulo o vacío", exception.getMessage());
    }

    @Test
    void testActualizarTarea() {
        when(tareaPersistence.findById(tarea.getId())).thenReturn(Optional.of(tarea));
        Tarea nuevaTarea = new Tarea();
        nuevaTarea.setNombre("Tarea Actualizada");
        nuevaTarea.setDescripcion("Descripción actualizada");
        nuevaTarea.setEstado(true);

        tareaService.actualizarTarea(tarea.getId(), nuevaTarea);

        ArgumentCaptor<Tarea> captor = ArgumentCaptor.forClass(Tarea.class);
        verify(tareaPersistence, times(1)).save(captor.capture());

        Tarea tareaGuardada = captor.getValue();
        assertEquals(nuevaTarea.getNombre(), tareaGuardada.getNombre());
        assertEquals(nuevaTarea.getDescripcion(), tareaGuardada.getDescripcion());
        assertEquals(nuevaTarea.getEstado(), tareaGuardada.getEstado());
    }

    @Test
    void testActualizarTareaNoExistente() {
        when(tareaPersistence.findById(tarea.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            tareaService.actualizarTarea(tarea.getId(), tarea);
        });

        assertEquals("Tarea con ID 1 no existe", exception.getMessage());
    }

    @Test
    void testCambiarEstadoTarea() {
        when(tareaPersistence.findById(tarea.getId())).thenReturn(Optional.of(tarea));

        boolean resultado = tareaService.cambiarEstado(tarea.getId());

        assertTrue(resultado);
        assertTrue(tarea.getEstado());
        verify(tareaPersistence, times(1)).save(tarea);
    }

    @Test
    void testCambiarEstadoTareaNoExistente() {
        when(tareaPersistence.findById(tarea.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            tareaService.cambiarEstado(tarea.getId());
        });

        assertEquals("Tarea con ID 1 no existe", exception.getMessage());
    }

    // PRUEBAS PARA LA PARTE 1 DEVOPS / CI-CD

    @Test
    void dadoUnaTareaRegistrada_CuandoLaConsulto_EntoncesLaConsultaEsExitosaYValidaElId() {
        // Crear una tarea de prueba
        Tarea tarea = new Tarea("1", "Test Task", "Descripción", false,null, 2, "Bajo", 6);

        // Configurar el mock para que retorne la tarea cuando se consulta por su ID
        when(tareaPersistence.findById("1")).thenReturn(Optional.of(tarea));

        // Ejecutar el método de servicio
        Tarea tareaConsultada = tareaService.obtenerTarea("1");

        // Validar que el ID es correcto
        assertEquals("1", tareaConsultada.getId());
        verify(tareaPersistence, times(1)).findById("1");
    }

    @Test
    void dadoNoHayTareasRegistradas_CuandoLaConsulto_EntoncesNoRetornaraResultado() {
        // Configurar el mock para que no retorne ninguna tarea
        when(tareaPersistence.findById("999")).thenReturn(Optional.empty());

        // Verificar que se lanza una excepción al consultar un ID inexistente
        assertThrows(IllegalArgumentException.class, () -> {
            tareaService.obtenerTarea("999");
        });

        verify(tareaPersistence, times(1)).findById("999");
    }

    @Test
    void dadoNoHayTareasRegistradas_CuandoCreoUnaTarea_EntoncesLaCreacionEsExitosa() {
        // Crear una tarea de prueba
        Tarea tarea = new Tarea(null, "Nueva Tarea", "Descripción", false,null, 3, "Bajo", 9);
        Tarea tareaConId = new Tarea("generated-id", "Nueva Tarea", "Descripción", false,null, 4, "Medio", 12);

        // Configurar el mock para que guarde la tarea y retorne una con ID generado
        when(tareaPersistence.save(any(Tarea.class))).thenReturn(tareaConId);

        // Ejecutar el método de servicio
        Tarea tareaCreada = tareaService.crear(tarea);

        // Validar que se ha generado un ID y que la tarea fue guardada correctamente
        assertNotNull(tareaCreada.getId());
        verify(tareaPersistence, times(1)).save(any(Tarea.class));
    }

    @Test
    void dadoUnaTareaRegistrada_CuandoLaElimino_EntoncesLaEliminacionEsExitosa() {
        // Crear una tarea de prueba
        Tarea tarea = new Tarea("2", "Tarea a eliminar", "Descripción", false,null,1, "Bajo", 2.2);

        // Configurar el mock para que retorne la tarea cuando se consulte por ID
        when(tareaPersistence.findById("2")).thenReturn(Optional.of(tarea));

        // Ejecutar la eliminación
        tareaService.eliminarTarea("2");

        // Verificar que se llamó a eliminar en el repositorio
        verify(tareaPersistence, times(1)).deleteById("2");
    }

    @Test
    void dadoUnaTareaRegistrada_CuandoLaEliminoYLaConsulto_EntoncesNoRetornaResultado() {
        // Crear una tarea de prueba
        Tarea tarea = new Tarea("3", "Otra Tarea a eliminar", "Descripción", false,null,3, "Medio", 5.5);

        // Configurar el mock para que retorne la tarea cuando se consulte por ID antes de eliminarla
        when(tareaPersistence.findById("3")).thenReturn(Optional.of(tarea)).thenReturn(Optional.empty());

        // Ejecutar la eliminación
        tareaService.eliminarTarea("3");

        // Verificar que la tarea ha sido eliminada y no puede ser consultada
        assertThrows(IllegalArgumentException.class, () -> {
            tareaService.obtenerTarea("3");
        });

        // Verificar las interacciones
        verify(tareaPersistence, times(2)).findById("3");  // Se llama para eliminar y consultar
        verify(tareaPersistence, times(1)).deleteById("3"); // Se llama para eliminar la tarea
    }


}
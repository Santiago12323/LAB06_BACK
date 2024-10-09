package edu.eci.cvds.AppTareas.repository;

import edu.eci.cvds.AppTareas.model.Tarea;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileTareaRepositoryTest {

    private Path tempFilePath;
    private FileTareaRepository fileTareaRepository;

    @BeforeEach
    void setUp() throws IOException {
        // Crear un archivo temporal para las pruebas
        tempFilePath = Files.createTempFile("tareas", ".txt");
        fileTareaRepository = new FileTareaRepository(tempFilePath.toString());
    }

    @AfterEach
    void tearDown() throws IOException {
        // Eliminar el archivo temporal al finalizar las pruebas
        Files.deleteIfExists(tempFilePath);
    }

    @Test
    void testSaveNewTarea() {
        Tarea tarea = new Tarea("1", "Tarea 1", "Descripción 1", false);
        Tarea savedTarea = fileTareaRepository.save(tarea);

        assertEquals(tarea.getId(), savedTarea.getId());
        assertEquals("Tarea 1", savedTarea.getNombre());
        assertEquals(1, fileTareaRepository.findAll().size());
    }

    @Test
    void testSaveExistingTarea() {
        Tarea tarea = new Tarea("1", "Tarea 1", "Descripción 1", false);
        fileTareaRepository.save(tarea);

        // Modifica la tarea existente
        tarea.setNombre("Tarea 1 Actualizada");
        Tarea updatedTarea = fileTareaRepository.save(tarea);

        assertEquals(tarea.getId(), updatedTarea.getId());
        assertEquals("Tarea 1 Actualizada", updatedTarea.getNombre());
    }

    @Test
    void testFindById() {
        Tarea tarea = new Tarea("1", "Prueba", "Descripción", false);
        fileTareaRepository.save(tarea);

        Tarea foundTarea = fileTareaRepository.findById("1").orElse(null);

        assertNotNull(foundTarea);
        assertEquals("Prueba", foundTarea.getNombre());
    }

    @Test
    void testDeleteById() {
        Tarea tarea = new Tarea("1", "Prueba 1", "Descripción 1", false);
        fileTareaRepository.save(tarea);

        fileTareaRepository.deleteById("1");

        assertFalse(fileTareaRepository.findById("1").isPresent());
        assertEquals(0, fileTareaRepository.findAll().size());
    }

    @Test
    void testFindAll() {
        assertEquals(0, fileTareaRepository.findAll().size()); // Verifica que no haya tareas inicialmente

        Tarea tarea1 = new Tarea("1", "Prueba 1", "Descripción 1", false);
        Tarea tarea2 = new Tarea("2", "Prueba 2", "Descripción 2", false);
        fileTareaRepository.save(tarea1);
        fileTareaRepository.save(tarea2);

        List<Tarea> tareas = fileTareaRepository.findAll();
        assertEquals(2, tareas.size()); // Verifica que se hayan guardado las dos tareas
    }
}


package edu.eci.cvds.AppTareas.config;

import edu.eci.cvds.AppTareas.repository.FileTareaRepository;
import edu.eci.cvds.AppTareas.repository.MongoTareaRepository;
import edu.eci.cvds.AppTareas.repository.TareaPersistence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TareaServiceConfigTest {

    private TareaServiceConfig tareaServiceConfig;

    @Mock
    private MongoTareaRepository mongoTareaRepository;

    @Mock
    private FileTareaRepository fileTareaRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testTareaServicePersistenceWithFile() {
        tareaServiceConfig = new TareaServiceConfig("file", mongoTareaRepository, fileTareaRepository);
        TareaPersistence result = tareaServiceConfig.tareaServicePersistence();
        assertEquals(fileTareaRepository, result);
    }

    @Test
    public void testTareaServicePersistenceWithMongoDB() {
        tareaServiceConfig = new TareaServiceConfig("mongoDB", mongoTareaRepository, fileTareaRepository);
        TareaPersistence result = tareaServiceConfig.tareaServicePersistence();
        assertEquals(mongoTareaRepository, result);
    }

    @Test
    public void testTareaServicePersistenceWithInvalidType() {
        tareaServiceConfig = new TareaServiceConfig("invalidType", mongoTareaRepository, fileTareaRepository);
        assertThrows(IllegalArgumentException.class, () -> {
            tareaServiceConfig.tareaServicePersistence();
        });
    }
}

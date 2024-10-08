package edu.eci.cvds.AppTareas.config;

import edu.eci.cvds.AppTareas.repository.FileTareaRepository;
import edu.eci.cvds.AppTareas.repository.TareaPersistence;
import edu.eci.cvds.AppTareas.repository.MongoTareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class TareaServiceConfig {

    @Value("${tarea.persistence}")
    private String persistenceType;

    @Autowired
    private MongoTareaRepository mongoTareaRepository;

    @Autowired
    private FileTareaRepository fileTareaRepository;

    @Bean
    @Primary // Establecemos este bean como principal
    public TareaPersistence tareaServicePersistence() {
        if ("file".equals(persistenceType)) {
            return fileTareaRepository;
        } else if ("mongoDB".equals(persistenceType)) {
            return mongoTareaRepository;
        } else {
            throw new IllegalArgumentException("Tipo de persistencia no soportado: " + persistenceType);
        }
    }
}

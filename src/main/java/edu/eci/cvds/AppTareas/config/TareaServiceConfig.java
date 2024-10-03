package edu.eci.cvds.AppTareas.config;

import edu.eci.cvds.AppTareas.repository.FileTareaRepository;
import edu.eci.cvds.AppTareas.repository.MongoTareaRepository;
import edu.eci.cvds.AppTareas.service.TareaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TareaServiceConfig {

    @Value("${tarea.persistence}")
    private String persistenceType;

    @Bean
    public TareaService tareaService(MongoTareaRepository mongoDB, FileTareaRepository file) {
        if ("file".equals(persistenceType)) {
            return new TareaService(file);
        } else {
            return new TareaService(mongoDB);
        }
    }
}

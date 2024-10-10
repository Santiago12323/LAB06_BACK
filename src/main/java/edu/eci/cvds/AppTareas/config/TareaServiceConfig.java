package edu.eci.cvds.AppTareas.config;

import edu.eci.cvds.AppTareas.repository.FileTareaRepository;
import edu.eci.cvds.AppTareas.repository.TareaPersistence;
import edu.eci.cvds.AppTareas.repository.mongo.MongoTareaRepository;
import edu.eci.cvds.AppTareas.repository.mysql.MySQLTareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class TareaServiceConfig {

    private final String persistenceType;
    private final MongoTareaRepository mongoTareaRepository;
    private final FileTareaRepository fileTareaRepository;
    private final MySQLTareaRepository mysqlTareaRepository;

    @Autowired
    public TareaServiceConfig(@Value("${tarea.persistence}") String persistenceType,
                              MongoTareaRepository mongoTareaRepository,
                              FileTareaRepository fileTareaRepository,
                              MySQLTareaRepository mysqlTareaRepository) {
        this.persistenceType = persistenceType;
        this.mongoTareaRepository = mongoTareaRepository;
        this.fileTareaRepository = fileTareaRepository;
        this.mysqlTareaRepository = mysqlTareaRepository;
    }

    @Bean
    @Primary
    public TareaPersistence tareaServicePersistence() {
        if ("file".equals(persistenceType)) {
            return fileTareaRepository;
        } else if ("mongoDB".equals(persistenceType)) {
            return mongoTareaRepository;
        } else if ("mysql".equals(persistenceType)) {
            return mysqlTareaRepository;
        } else {
            throw new IllegalArgumentException("Tipo de persistencia no soportado: " + persistenceType);
        }
    }
}
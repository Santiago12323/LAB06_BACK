package edu.eci.cvds.AppTareas.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "edu.eci.cvds.AppTareas.repository.mongo")
@EnableMongoAuditing
public class MongoConfig {
    // Configuración de MongoDB
}

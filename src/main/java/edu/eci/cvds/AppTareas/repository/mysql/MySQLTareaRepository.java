package edu.eci.cvds.AppTareas.repository.mysql;

import edu.eci.cvds.AppTareas.model.Tarea;
import edu.eci.cvds.AppTareas.repository.TareaPersistence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("mysql")
public interface MySQLTareaRepository extends JpaRepository<Tarea, String>, TareaPersistence {
}
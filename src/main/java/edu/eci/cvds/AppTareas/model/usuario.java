package edu.eci.cvds.AppTareas.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document(collection = "tareas")
@Entity
@Table(name = "tareas")
@Data
public class usuario {
    @Id
    private String id;
    private String nombre;
    private String contraseña;
    @OneToMany(mappedBy = "usuarioId")
    private List<Tarea> tareas;


    public usuario(String contraseña, String nombre, String id) {
        this.contraseña = contraseña;
        this.nombre = nombre;
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getContraseña() {
        return contraseña;
    }

    public List<Tarea> getTareas() {
        return tareas;
    }
}

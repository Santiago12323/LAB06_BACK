package edu.eci.cvds.AppTareas.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tareas")
@Data
public class Tarea {
    @Id
    private String id;
    private String nombre;
    private String descripcion;
    private boolean estado;

    private int prioridad;
    private String dificultad;
    private double tiempoPromedio;

    @Column(name = "usuarioNombre")
    private String usuarioNombre;

    public Tarea(String id, String nombre, String descripcion, boolean estado, String usuarioNombre, int prioridad, String dificultad, double tiempoPromedio) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
        this.prioridad = prioridad;
        this.dificultad = dificultad;
        this.tiempoPromedio = tiempoPromedio;
    }

    public Tarea(){

    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean getEstado() {
        return estado;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }


    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }
}

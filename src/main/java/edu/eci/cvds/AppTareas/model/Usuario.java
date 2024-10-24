package edu.eci.cvds.AppTareas.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "tareas")
@Entity
@Table(name = "tareas")
@Data
public class Usuario {
    @Id
    private String id;
    private String nombre;
    private String contrasena;
    @OneToMany(mappedBy = "usuarioNombre")
    private List<Tarea> tareas;

    public Usuario(String contrasena, String nombre, String id) {
        this.contrasena = encriptarContrasena(contrasena);
        this.nombre = nombre;
        this.id = id;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = encriptarContrasena(contrasena);
    }

    private String encriptarContrasena(String contrasena) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(contrasena);
    }
}

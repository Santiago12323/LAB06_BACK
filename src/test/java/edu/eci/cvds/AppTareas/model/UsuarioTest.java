package edu.eci.cvds.AppTareas.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    private Usuario usuario;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        usuario = new Usuario("contraseña123", "Juan", "1");
    }

    @Test
    void testGetId() {
        assertEquals("1", usuario.getId());
    }

    @Test
    void testGetNombre() {
        assertEquals("Juan", usuario.getNombre());
    }

    @Test
    void testSetId() {
        usuario.setId("2");
        assertEquals("2", usuario.getId());
    }

    @Test
    void testSetNombre() {
        usuario.setNombre("Pedro");
        assertEquals("Pedro", usuario.getNombre());
    }

    @Test
    void testSetContrasena() {
        usuario.setContrasena("nuevaContraseña");
        assertTrue(passwordEncoder.matches("nuevaContraseña", usuario.getContrasena()));
    }
}
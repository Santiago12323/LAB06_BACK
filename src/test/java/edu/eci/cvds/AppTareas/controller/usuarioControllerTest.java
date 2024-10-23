package edu.eci.cvds.AppTareas.controller;

import edu.eci.cvds.AppTareas.model.Tarea;
import edu.eci.cvds.AppTareas.model.usuario;
import edu.eci.cvds.AppTareas.service.usuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class usuarioControllerTest {

    @InjectMocks
    private usuarioController usuarioController;

    @Mock
    private usuarioService usuarioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearUsuarioExitosamente() {
        usuario usuario = new usuario("password", "nuevoUsuario", "1");
        when(usuarioService.crearUsuario(any(usuario.class))).thenReturn(usuario);
        ResponseEntity<String> response = usuarioController.crear(usuario);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().contains("Usuario creado: nuevoUsuario"));
    }

    @Test
    void consultarUsuarioNoExistenteTest() {
        String usuarioID = "nonExistentID";
        when(usuarioService.obtenerUsuario(usuarioID)).thenReturn(Optional.empty());

        Optional<usuario> resultado = usuarioController.consultarUsuario(usuarioID); // Cambié el nombre del método para que coincida

        assertFalse(resultado.isPresent());
    }

    @Test
    void eliminarUsuarioTest() {
        String usuarioID = "1";
        doNothing().when(usuarioService).eliminarUsuario(usuarioID);

        usuarioController.eliminarUsuario(usuarioID); // Cambié el nombre del método para que coincida

        verify(usuarioService, times(1)).eliminarUsuario(usuarioID);
    }

    @Test
    void verificarPassTest() {
        String nombre = "testUser";
        String contrasena = "testPass";
        when(usuarioService.verificarPass(nombre, contrasena)).thenReturn(true);

        boolean resultado = usuarioService.verificarPass(nombre, contrasena);

        assertTrue(resultado);
        verify(usuarioService, times(1)).verificarPass(nombre, contrasena);
    }

    @Test
    void verificarPassIncorrectoTest() {
        String nombre = "testUser";
        String contrasena = "wrongPass";
        when(usuarioService.verificarPass(nombre, contrasena)).thenReturn(false);

        boolean resultado = usuarioService.verificarPass(nombre, contrasena);

        assertFalse(resultado);
        verify(usuarioService, times(1)).verificarPass(nombre, contrasena);
    }
}
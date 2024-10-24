package edu.eci.cvds.AppTareas.controller;

import edu.eci.cvds.AppTareas.model.Usuario;
import edu.eci.cvds.AppTareas.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UsuarioControllerTest {

    @InjectMocks
    private UsuarioController usuarioController;

    @Mock
    private UsuarioService usuarioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearUsuarioExitosamente() {
        Usuario usuario = new Usuario("password", "nuevoUsuario", "1");
        when(usuarioService.crearUsuario(any(Usuario.class))).thenReturn(usuario);
        ResponseEntity<String> response = usuarioController.crear(usuario);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().contains("Usuario creado: nuevoUsuario"));
    }

    @Test
    void testConsultarUsuarioNoExistente() {
        String usuarioID = "nonExistentID";
        when(usuarioService.obtenerUsuario(usuarioID)).thenReturn(Optional.empty());

        Optional<Usuario> resultado = usuarioController.consultarUsuario(usuarioID);

        assertFalse(resultado.isPresent());
    }

    @Test
    void testEliminarUsuario() {
        String usuarioID = "1";
        doNothing().when(usuarioService).eliminarUsuario(usuarioID);

        usuarioController.eliminarUsuario(usuarioID);

        verify(usuarioService, times(1)).eliminarUsuario(usuarioID);
    }

    @Test
    void testVerificarPassCorrecto() {
        String nombre = "testUser";
        String contrasena = "testPass";
        when(usuarioService.verificarPass(nombre, contrasena)).thenReturn(true);

        boolean resultado = usuarioService.verificarPass(nombre, contrasena);

        assertTrue(resultado);
        verify(usuarioService, times(1)).verificarPass(nombre, contrasena);
    }

    @Test
    void testVerificarPassIncorrecto() {
        String nombre = "testUser";
        String contrasena = "wrongPass";
        when(usuarioService.verificarPass(nombre, contrasena)).thenReturn(false);

        boolean resultado = usuarioService.verificarPass(nombre, contrasena);

        assertFalse(resultado);
        verify(usuarioService, times(1)).verificarPass(nombre, contrasena);
    }
}
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

import java.util.Arrays;
import java.util.List;
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


    @Test
    void testCrearUsuarioConNombreNulo() {
        Usuario nuevoUsuario = new Usuario("password", null, "1"); // Nombre nulo
        when(usuarioService.crearUsuario(any(Usuario.class))).thenThrow(new IllegalArgumentException("El nombre no puede ser nulo"));

        ResponseEntity<String> response = usuarioController.crear(nuevoUsuario);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("El nombre no puede ser nulo", response.getBody());
        verify(usuarioService, times(1)).crearUsuario(nuevoUsuario);
    }

    @Test
    void testConsultarUsuarios() {
        Usuario usuario1 = new Usuario("pass1", "user1", "1");
        Usuario usuario2 = new Usuario("pass2", "user2", "2");
        List<Usuario> listaUsuarios = Arrays.asList(usuario1, usuario2);

        when(usuarioService.obtenerUsuarios()).thenReturn(listaUsuarios);

        List<Usuario> resultado = usuarioController.consultarUsuarios();

        assertEquals(2, resultado.size());
        assertEquals("user1", resultado.get(0).getNombre());
        assertEquals("user2", resultado.get(1).getNombre());
        verify(usuarioService, times(1)).obtenerUsuarios();
    }

    @Test
    void testEliminarUsuarioNoExistente() {
        String usuarioID = "nonExistentID";
        when(usuarioService.obtenerUsuario(usuarioID)).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> usuarioController.eliminarUsuario(usuarioID));
        verify(usuarioService, times(1)).eliminarUsuario(usuarioID);
    }

    @Test
    void testVerificarPassUsuarioNoExistente() {
        String nombre = "nonExistentUser";
        String contrasena = "anyPass";
        when(usuarioService.encontrarUsuario(nombre)).thenReturn(null);

        boolean resultado = usuarioController.verificarPass(nombre, contrasena);

        assertFalse(resultado);
        verify(usuarioService, times(1)).encontrarUsuario(nombre);
    }

}
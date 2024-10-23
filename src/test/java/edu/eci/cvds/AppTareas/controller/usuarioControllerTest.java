package edu.eci.cvds.AppTareas.controller;

import edu.eci.cvds.AppTareas.model.Tarea;
import edu.eci.cvds.AppTareas.model.usuario;
import edu.eci.cvds.AppTareas.service.usuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
    void crearUsuarioTest() {
        usuario nuevoUsuario = new usuario("testUser", "testPass", "1");
        when(usuarioService.crearUsuario(any(usuario.class))).thenReturn(nuevoUsuario);

        usuario resultado = usuarioController.crear(nuevoUsuario);

        assertNotNull(resultado);
        assertEquals("testUser", resultado.getNombre());
        verify(usuarioService, times(1)).crearUsuario(any(usuario.class));
    }

    @Test
    void consultarUsuariosTest() {
        usuario usuario1 = new usuario("user1", "pass1", "1");
        usuario usuario2 = new usuario("user2", "pass2", "2");
        when(usuarioService.obtenerUsuarios()).thenReturn(Arrays.asList(usuario1, usuario2));

        List<usuario> usuarios = usuarioController.consultarUsuarios();

        assertEquals(2, usuarios.size());
        assertEquals("user1", usuarios.get(0).getNombre());
        assertEquals("user2", usuarios.get(1).getNombre());
    }

    @Test
    void consultarUsuarioTest() {
        String usuarioID = "1";
        usuario usuario = new usuario("testUser", "testPass", usuarioID);
        when(usuarioService.obtenerUsuario(usuarioID)).thenReturn(Optional.of(usuario));

        Optional<usuario> resultado = usuarioController.consultarUsuario(usuarioID); // Cambié el nombre del método para que coincida

        assertTrue(resultado.isPresent());
        assertEquals("testUser", resultado.get().getNombre());
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

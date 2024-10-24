package edu.eci.cvds.AppTareas.service;

import edu.eci.cvds.AppTareas.model.Tarea;
import edu.eci.cvds.AppTareas.model.usuario;
import edu.eci.cvds.AppTareas.repository.mongo.MongoUsuarioRepository;
import edu.eci.cvds.AppTareas.repository.mongo.MongoTareaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class usuarioServiceTest {

    private MongoUsuarioRepository mockUsuarioRepository;
    private MongoTareaRepository mockTareaRepository;
    private usuarioService usuarioService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @BeforeEach
    void setUp() {
        mockUsuarioRepository = Mockito.mock(MongoUsuarioRepository.class);
        mockTareaRepository = Mockito.mock(MongoTareaRepository.class);
        usuarioService = new usuarioService(mockUsuarioRepository, mockTareaRepository);
    }

    @Test
    void testCrearUsuario() {
        usuario newUser = new usuario("1", "testUser", "password");
        when(mockUsuarioRepository.save(any(usuario.class))).thenReturn(newUser);

        usuario createdUser = usuarioService.crearUsuario(newUser);

        assertEquals(newUser, createdUser);
        verify(mockUsuarioRepository).save(newUser);
    }

    @Test
    void testObtenerUsuarios() {
        usuario user1 = new usuario("1", "user1", "pass1");
        usuario user2 = new usuario("2", "user2", "pass2");
        when(mockUsuarioRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        List<usuario> usuarios = usuarioService.obtenerUsuarios();

        assertEquals(2, usuarios.size());
        assertTrue(usuarios.contains(user1));
        assertTrue(usuarios.contains(user2));
    }

    @Test
    void testObtenerUsuario() {
        usuario user = new usuario("1", "user", "pass");
        when(mockUsuarioRepository.findById("1")).thenReturn(Optional.of(user));

        Optional<usuario> foundUser = usuarioService.obtenerUsuario("1");

        assertTrue(foundUser.isPresent());
        assertEquals(user, foundUser.get());
    }

    @Test
    void testEliminarUsuario() {
        usuario user = new usuario("1", "user", "pass");
        when(mockUsuarioRepository.findById("1")).thenReturn(Optional.of(user));

        usuarioService.eliminarUsuario("1");

        verify(mockUsuarioRepository).deleteById("1");
    }

    @Test
    void testObtenerUsuarios_Vacio() {
        // Configura el repositorio simulado para devolver una lista vacía
        when(mockUsuarioRepository.findAll()).thenReturn(Collections.emptyList());

        // Llama al método obtenerUsuarios
        List<usuario> usuarios = usuarioService.obtenerUsuarios();

        // Verifica que la lista de usuarios está vacía
        assertTrue(usuarios.isEmpty());
    }





    @Test
    void testObtenerTareasDeUsuario() {
        Tarea tarea1 = new Tarea("1", "Tarea 1", "Descripción 1", true, "coronado" , 1, "Baja", 2.0);
        Tarea tarea2 = new Tarea("2", "Tarea 2", "Descripción 2", false,"coronado", 2, "Media", 3.5);
        when(mockTareaRepository.findByUsuarioNombre("coronado")).thenReturn(Arrays.asList(tarea1, tarea2));

        List<Tarea> tareas = usuarioService.obtenerTareasDeUsuario("coronado");

        assertEquals(2, tareas.size());
        assertTrue(tareas.contains(tarea1));
        assertTrue(tareas.contains(tarea2));
    }

    @Test
    void testCrearUsuario_Nuevo() {
        usuario newUser = new usuario("2", "newUser", "password");
        when(mockUsuarioRepository.save(any(usuario.class))).thenReturn(newUser);

        usuario createdUser = usuarioService.crearUsuario(newUser);

        assertEquals(newUser, createdUser);
        verify(mockUsuarioRepository).save(newUser);
    }


    @Test
    void testObtenerUsuario_NoExistente() {
        when(mockUsuarioRepository.findById("nonExistentId")).thenReturn(Optional.empty());

        Optional<usuario> foundUser = usuarioService.obtenerUsuario("nonExistentId");

        assertFalse(foundUser.isPresent());
    }

    @Test
    void testPreliminarily_NoExistence() {
        when(mockUsuarioRepository.findById("nonExistentId")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> usuarioService.eliminarUsuario("nonExistentId"));
    }

    @Test
    void testObtenerTareasDeUsuario_Vacio() {
        when(mockTareaRepository.findByUsuarioNombre("usuarioInexistente")).thenReturn(Collections.emptyList());

        List<Tarea> tareas = usuarioService.obtenerTareasDeUsuario("usuarioInexistente");

        assertTrue(tareas.isEmpty());
    }

    @Test
    void testObtenerTareasDeUsuario_SoloUnaTarea() {
        Tarea tarea1 = new Tarea("3", "Tarea 3", "Descripción 3", true, "usuarioTest", 1, "Baja", 2.0);
        when(mockTareaRepository.findByUsuarioNombre("usuarioTest")).thenReturn(Collections.singletonList(tarea1));

        List<Tarea> tareas = usuarioService.obtenerTareasDeUsuario("usuarioTest");

        assertEquals(1, tareas.size());
        assertEquals(tarea1, tareas.get(0));
    }

    @Test
    void testVerificarPass_Correcta() {
        String nombreUsuario = "testUser";
        String password = "correctPassword";

        usuario testUser = new usuario("correctPassword", nombreUsuario, "1");

        boolean result = usuarioService.verificarPass(nombreUsuario, password);

        assertFalse(result);
    }

    @Test
    void testVerificarPass_Incorrecta() {
        String nombreUsuario = "testUser";
        String password = "incorrectPassword";
        String encryptedPassword = passwordEncoder.encode("correctPassword");

        usuario testUser = new usuario("1", nombreUsuario, encryptedPassword);
        when(mockUsuarioRepository.findByNombre(nombreUsuario)).thenReturn(testUser);

        boolean result = usuarioService.verificarPass(nombreUsuario, password);

        assertFalse(result);
    }

    @Test
    void testVerificarPass_UsuarioNoExistente() {
        String nombreUsuario = "nonExistentUser";
        String password = "anyPassword";

        when(mockUsuarioRepository.findByNombre(nombreUsuario)).thenReturn(null); // No se encuentra el usuario

        boolean result = usuarioService.verificarPass(nombreUsuario, password);

        assertFalse(result);
    }

    @Test
    void testVerificarPass_ContraseñaFalse() {
        String nombreUsuario = "testUser";
        String encryptedPassword = passwordEncoder.encode("correctPassword");

        usuario testUser = new usuario("correctPassword", nombreUsuario,"1");

        boolean result = usuarioService.verificarPass(testUser.getContraseña(),encryptedPassword ); // Probar con contraseña nula

        assertFalse(result);
    }


    @Test
    void testEncontrarUsuario_Existente() {
        String nombreUsuario = "testUser";
        usuario testUser = new usuario("1", nombreUsuario, "encryptedPassword");

        when(mockUsuarioRepository.findByNombre(nombreUsuario)).thenReturn(testUser);

        usuario foundUser = usuarioService.encontrarUsuario(nombreUsuario);

        assertNotNull(foundUser);
        assertEquals(testUser, foundUser);
    }

    @Test
    void testEncontrarUsuario_NoExistente() {
        String nombreUsuario = "nonExistentUser";

        when(mockUsuarioRepository.findByNombre(nombreUsuario)).thenReturn(null);

        usuario foundUser = usuarioService.encontrarUsuario(nombreUsuario);

        assertNull(foundUser);
    }

    @Test
    void testEncontrarUsuario_NombreEnBlanco() {
        String nombreUsuario = "";


        when(mockUsuarioRepository.findByNombre(nombreUsuario)).thenReturn(null);

        usuario foundUser = usuarioService.encontrarUsuario(nombreUsuario);

        assertNull(foundUser);
    }

    @Test
    void testEncontrarUsuario_NombreNulo() {
        String nombreUsuario = null;

        when(mockUsuarioRepository.findByNombre(nombreUsuario)).thenReturn(null);

        usuario foundUser = usuarioService.encontrarUsuario(nombreUsuario);

        assertNull(foundUser);
    }

    @Test
    void testCrearUsuario_UsuarioExistente() {
        usuario existingUser = new usuario("1", "existingUser", "password");
        when(mockUsuarioRepository.findByNombre(existingUser.getNombre())).thenReturn(existingUser); // El usuario ya existe

        assertThrows(RuntimeException.class, () -> usuarioService.crearUsuario(existingUser));
    }


    @Test
    void testCrearUsuario_SinNombre() {
        usuario newUser = new usuario("pass", null, "1"); // Nombre nulo

        assertDoesNotThrow(() -> usuarioService.crearUsuario(newUser));

    }


    @Test
    void testPreliminarily_NoExisten() {
        when(mockUsuarioRepository.findById("nonExistentId")).thenReturn(Optional.empty()); // El usuario no existe

        assertThrows(RuntimeException.class, () -> usuarioService.eliminarUsuario("nonExistentId"));
    }

    @Test
    void testEliminarUsuario_IdNulo() {
        assertThrows(RuntimeException.class, () -> usuarioService.eliminarUsuario(null)); // ID nulo
    }




}
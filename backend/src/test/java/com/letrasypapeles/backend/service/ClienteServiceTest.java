package com.letrasypapeles.backend.service;

import com.letrasypapeles.backend.entity.Cliente;
import com.letrasypapeles.backend.entity.Role;
import com.letrasypapeles.backend.repository.ClienteRepository;
import com.letrasypapeles.backend.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ClienteServiceTest {

    private ClienteRepository clienteRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        // Se crean los mocks necesarios para el servicio
        clienteRepository = mock(ClienteRepository.class);
        roleRepository = mock(RoleRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        clienteService = new ClienteService(
                clienteRepository,
                roleRepository,
                passwordEncoder
        );
    }

    @Test
    void testObtenerTodos() {
        // Prueba obtener todos los clientes
        Cliente c1 = new Cliente(1L, "Ana", "ana@mail.com", "pass", 10, Set.of());
        when(clienteRepository.findAll()).thenReturn(List.of(c1));

        List<Cliente> result = clienteService.obtenerTodos();

        assertEquals(1, result.size());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void testObtenerPorId() {
        // Prueba obtener cliente por ID
        Cliente c = new Cliente(2L, "Luis", "luis@mail.com", "pass", 5, Set.of());
        when(clienteRepository.findById(2L)).thenReturn(Optional.of(c));

        Optional<Cliente> result = clienteService.obtenerPorId(2L);

        assertTrue(result.isPresent());
        assertEquals("Luis", result.get().getNombre());
    }

    @Test
    void testObtenerPorEmail() {
        // Prueba obtener cliente por email
        Cliente c = new Cliente(3L, "Pedro", "pedro@mail.com", "pass", 20, Set.of());
        when(clienteRepository.findByEmail("pedro@mail.com")).thenReturn(Optional.of(c));

        Optional<Cliente> result = clienteService.obtenerPorEmail("pedro@mail.com");

        assertTrue(result.isPresent());
        assertEquals("Pedro", result.get().getNombre());
    }

    @Test
    void testRegistrarClienteNuevo() {
        // Prueba registrar un cliente nuevo
        Cliente nuevo = new Cliente(null, "Marta", "marta@mail.com", "1234", null, null);
        Role rolCliente = new Role("CLIENTE");

        when(clienteRepository.existsByEmail("marta@mail.com")).thenReturn(false);
        when(passwordEncoder.encode("1234")).thenReturn("hashed1234");
        when(roleRepository.findByNombre("CLIENTE")).thenReturn(Optional.of(rolCliente));
        when(clienteRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        Cliente result = clienteService.registrarCliente(nuevo);

        assertEquals("hashed1234", result.getContraseña());
        assertEquals(0, result.getPuntosFidelidad());
        assertTrue(result.getRoles().stream().anyMatch(r -> r.getNombre().equals("CLIENTE")));
        verify(clienteRepository, times(1)).save(any());
    }

    @Test
    void testRegistrarClienteExistente() {
        // Prueba intentar registrar un cliente cuyo email ya existe
        Cliente existente = new Cliente(null, "Laura", "laura@mail.com", "1234", null, null);
        when(clienteRepository.existsByEmail("laura@mail.com")).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                clienteService.registrarCliente(existente)
        );

        assertEquals("El correo electrónico ya está registrado.", ex.getMessage());
    }

    @Test
    void testActualizarCliente() {
        // Prueba actualizar cliente existente
        Cliente cli = new Cliente(10L, "Raul", "raul@mail.com", "abc", 0, null);
        when(clienteRepository.save(cli)).thenReturn(cli);

        Cliente result = clienteService.actualizarCliente(cli);

        assertEquals(cli.getId(), result.getId());
        verify(clienteRepository, times(1)).save(cli);
    }

    @Test
    void testEliminar() {
        // Prueba eliminar cliente por ID
        clienteService.eliminar(55L);
        verify(clienteRepository, times(1)).deleteById(55L);
    }
}

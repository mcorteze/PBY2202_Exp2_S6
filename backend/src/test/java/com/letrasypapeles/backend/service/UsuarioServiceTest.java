package com.letrasypapeles.backend.service;

import com.letrasypapeles.backend.entity.Cliente;
import com.letrasypapeles.backend.entity.Role;
import com.letrasypapeles.backend.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioServiceTest {

    private ClienteRepository clienteRepository;
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        // Se crea el mock del repositorio y se instancia el servicio
        clienteRepository = mock(ClienteRepository.class);
        usuarioService = new UsuarioService(clienteRepository);
    }

    @Test
    void testLoadUserByUsername_Success() {
        // Prueba carga exitosa de usuario por email
        Cliente cliente = new Cliente(
                1L,
                "Manuel",
                "manuel@mail.com",
                "encryptedpass",
                0,
                Set.of(new Role("CLIENTE"))
        );

        when(clienteRepository.findByEmail("manuel@mail.com"))
                .thenReturn(Optional.of(cliente));

        UserDetails userDetails = usuarioService.loadUserByUsername("manuel@mail.com");

        assertEquals("manuel@mail.com", userDetails.getUsername());
        assertEquals("encryptedpass", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_CLIENTE")));
    }

    @Test
    void testLoadUserByUsername_NotFound() {
        // Prueba escenario en el que el usuario no existe
        when(clienteRepository.findByEmail("inexistente@mail.com"))
                .thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            usuarioService.loadUserByUsername("inexistente@mail.com");
        });
    }
}

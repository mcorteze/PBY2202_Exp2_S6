package com.letrasypapeles.backend.controller;

import com.letrasypapeles.backend.entity.Cliente;
import com.letrasypapeles.backend.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ClienteControllerTest {

    private ClienteService clienteService;
    private ClienteController clienteController;

    @BeforeEach
    void setUp() {
        // Se crean los mocks necesarios para el controlador
        clienteService = mock(ClienteService.class);
        clienteController = new ClienteController(clienteService);
    }

    @Test
    void testObtenerTodos() {
        // Simula retorno de lista de clientes
        when(clienteService.obtenerTodos()).thenReturn(List.of(new Cliente()));

        ResponseEntity<List<Cliente>> response = clienteController.obtenerTodos();

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testObtenerPorIdFound() {
        // Simula búsqueda exitosa por ID
        Cliente cli = new Cliente(1L, "Pepe", "pepe@test.com", "1234", 0, null);
        when(clienteService.obtenerPorId(1L)).thenReturn(Optional.of(cli));

        ResponseEntity<Cliente> response = clienteController.obtenerPorId(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Pepe", response.getBody().getNombre());
    }

    @Test
    void testObtenerPorIdNotFound() {
        // Simula búsqueda por ID que no encuentra datos
        when(clienteService.obtenerPorId(99L)).thenReturn(Optional.empty());

        ResponseEntity<Cliente> response = clienteController.obtenerPorId(99L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testRegistrarCliente() {
        // Simula registro exitoso de cliente
        Cliente cli = new Cliente();
        cli.setId(1L);
        cli.setNombre("Nuevo");

        when(clienteService.registrarCliente(cli)).thenReturn(cli);

        ResponseEntity<Cliente> response = clienteController.registrarCliente(cli);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Nuevo", response.getBody().getNombre());
    }

    @Test
    void testActualizarCliente() {
        // Simula actualización exitosa de cliente existente
        Cliente existing = new Cliente(1L, "Viejo", "old@test.com", "123", 0, null);
        Cliente updated = new Cliente(1L, "Actualizado", "updated@test.com", "456", 0, null);

        when(clienteService.obtenerPorId(1L)).thenReturn(Optional.of(existing));
        when(clienteService.actualizarCliente(updated)).thenReturn(updated);

        ResponseEntity<Cliente> response = clienteController.actualizarCliente(1L, updated);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Actualizado", response.getBody().getNombre());
    }

    @Test
    void testActualizarClienteNotFound() {
        // Simula intento de actualizar cliente inexistente
        when(clienteService.obtenerPorId(99L)).thenReturn(Optional.empty());

        ResponseEntity<Cliente> response = clienteController.actualizarCliente(99L, new Cliente());

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testEliminarCliente() {
        // Simula eliminación exitosa de cliente existente
        Cliente cli = new Cliente(1L, "Pepe", "pepe@test.com", "1234", 0, null);
        when(clienteService.obtenerPorId(1L)).thenReturn(Optional.of(cli));

        ResponseEntity<Void> response = clienteController.eliminarCliente(1L);

        assertEquals(200, response.getStatusCodeValue());
        verify(clienteService).eliminar(1L);
    }

    @Test
    void testEliminarClienteNotFound() {
        // Simula intento de eliminar cliente inexistente
        when(clienteService.obtenerPorId(99L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = clienteController.eliminarCliente(99L);

        assertEquals(404, response.getStatusCodeValue());
    }
}

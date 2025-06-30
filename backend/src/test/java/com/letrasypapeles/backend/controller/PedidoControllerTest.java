package com.letrasypapeles.backend.controller;

import com.letrasypapeles.backend.entity.Pedido;
import com.letrasypapeles.backend.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PedidoControllerTest {

    private PedidoService pedidoService;
    private PedidoController pedidoController;

    @BeforeEach
    void setUp() {
        // Se crean los mocks necesarios para el controlador
        pedidoService = mock(PedidoService.class);
        pedidoController = new PedidoController(pedidoService);
    }

    @Test
    void testObtenerTodos() {
        // Simula retorno de lista de pedidos
        when(pedidoService.obtenerTodos()).thenReturn(List.of(new Pedido()));
        ResponseEntity<List<Pedido>> response = pedidoController.obtenerTodos();
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testObtenerPorIdFound() {
        // Simula búsqueda exitosa por ID
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        when(pedidoService.obtenerPorId(1L)).thenReturn(Optional.of(pedido));
        ResponseEntity<Pedido> response = pedidoController.obtenerPorId(1L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testObtenerPorIdNotFound() {
        // Simula búsqueda por ID que no encuentra datos
        when(pedidoService.obtenerPorId(99L)).thenReturn(Optional.empty());
        ResponseEntity<Pedido> response = pedidoController.obtenerPorId(99L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testObtenerPorClienteId() {
        // Simula búsqueda de pedidos por ID de cliente
        when(pedidoService.obtenerPorClienteId(5L))
                .thenReturn(List.of(new Pedido()));
        ResponseEntity<List<Pedido>> response = pedidoController.obtenerPorClienteId(5L);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testCrearPedido() {
        // Simula creación exitosa de pedido
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        when(pedidoService.guardar(pedido)).thenReturn(pedido);
        ResponseEntity<Pedido> response = pedidoController.crearPedido(pedido);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testActualizarPedidoFound() {
        // Simula actualización exitosa de pedido existente
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        when(pedidoService.obtenerPorId(1L)).thenReturn(Optional.of(pedido));
        when(pedidoService.guardar(pedido)).thenReturn(pedido);
        ResponseEntity<Pedido> response = pedidoController.actualizarPedido(1L, pedido);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testActualizarPedidoNotFound() {
        // Simula intento de actualizar pedido inexistente
        when(pedidoService.obtenerPorId(99L)).thenReturn(Optional.empty());
        ResponseEntity<Pedido> response = pedidoController.actualizarPedido(99L, new Pedido());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testEliminarPedidoFound() {
        // Simula eliminación exitosa de pedido existente
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        when(pedidoService.obtenerPorId(1L)).thenReturn(Optional.of(pedido));
        ResponseEntity<Void> response = pedidoController.eliminarPedido(1L);
        assertEquals(200, response.getStatusCodeValue());
        verify(pedidoService).eliminar(1L);
    }

    @Test
    void testEliminarPedidoNotFound() {
        // Simula intento de eliminar pedido inexistente
        when(pedidoService.obtenerPorId(99L)).thenReturn(Optional.empty());
        ResponseEntity<Void> response = pedidoController.eliminarPedido(99L);
        assertEquals(404, response.getStatusCodeValue());
    }
}

package com.letrasypapeles.backend.service;

import com.letrasypapeles.backend.entity.Pedido;
import com.letrasypapeles.backend.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PedidoServiceTest {

    private PedidoRepository pedidoRepository;
    private PedidoService pedidoService;

    @BeforeEach
    void setUp() {
        // Se crea el mock del repositorio y se instancia el servicio
        pedidoRepository = mock(PedidoRepository.class);
        pedidoService = new PedidoService(pedidoRepository);
    }

    @Test
    void testObtenerTodos() {
        // Prueba obtener todos los pedidos
        when(pedidoRepository.findAll()).thenReturn(List.of(new Pedido()));

        List<Pedido> result = pedidoService.obtenerTodos();

        assertEquals(1, result.size());
    }

    @Test
    void testObtenerPorId() {
        // Prueba obtener pedido por ID
        Pedido p = new Pedido(1L, null, "CREADO", null, null);
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(p));

        Optional<Pedido> result = pedidoService.obtenerPorId(1L);

        assertTrue(result.isPresent());
        assertEquals("CREADO", result.get().getEstado());
    }

    @Test
    void testGuardar() {
        // Prueba guardar un pedido
        Pedido p = new Pedido();
        when(pedidoRepository.save(p)).thenReturn(p);

        Pedido result = pedidoService.guardar(p);

        assertNotNull(result);
        verify(pedidoRepository).save(p);
    }

    @Test
    void testEliminar() {
        // Prueba eliminar pedido por ID
        pedidoService.eliminar(10L);
        verify(pedidoRepository).deleteById(10L);
    }

    @Test
    void testObtenerPorClienteId() {
        // Prueba obtener pedidos por ID de cliente
        when(pedidoRepository.findByClienteId(4L)).thenReturn(List.of(new Pedido()));

        List<Pedido> result = pedidoService.obtenerPorClienteId(4L);

        assertEquals(1, result.size());
    }

    @Test
    void testObtenerPorEstado() {
        // Prueba obtener pedidos por estado
        when(pedidoRepository.findByEstado("ENTREGADO")).thenReturn(List.of(new Pedido()));

        List<Pedido> result = pedidoService.obtenerPorEstado("ENTREGADO");

        assertEquals(1, result.size());
    }
}

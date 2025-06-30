package com.letrasypapeles.backend.controller;

import com.letrasypapeles.backend.entity.Reserva;
import com.letrasypapeles.backend.service.ReservaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ReservaControllerTest {

    private ReservaService reservaService;
    private ReservaController reservaController;

    @BeforeEach
    void setUp() {
        // Se crean los mocks necesarios para el controlador
        reservaService = mock(ReservaService.class);
        reservaController = new ReservaController(reservaService);
    }

    @Test
    void testObtenerTodas() {
        // Simula retorno de lista de reservas
        when(reservaService.obtenerTodas()).thenReturn(List.of(new Reserva()));
        ResponseEntity<List<Reserva>> response = reservaController.obtenerTodas();
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testObtenerPorIdFound() {
        // Simula búsqueda exitosa por ID
        Reserva reserva = new Reserva();
        reserva.setId(1L);
        when(reservaService.obtenerPorId(1L)).thenReturn(Optional.of(reserva));
        ResponseEntity<Reserva> response = reservaController.obtenerPorId(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void testObtenerPorIdNotFound() {
        // Simula búsqueda por ID que no encuentra datos
        when(reservaService.obtenerPorId(99L)).thenReturn(Optional.empty());
        ResponseEntity<Reserva> response = reservaController.obtenerPorId(99L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testObtenerPorClienteId() {
        // Simula búsqueda de reservas por ID de cliente
        when(reservaService.obtenerPorClienteId(5L)).thenReturn(List.of(new Reserva()));
        ResponseEntity<List<Reserva>> response = reservaController.obtenerPorClienteId(5L);
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testObtenerPorProductoId() {
        // Simula búsqueda de reservas por ID de producto
        when(reservaService.obtenerPorProductoId(3L)).thenReturn(List.of(new Reserva()));
        ResponseEntity<List<Reserva>> response = reservaController.obtenerPorProductoId(3L);
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testObtenerPorEstado() {
        // Simula búsqueda de reservas por estado
        when(reservaService.obtenerPorEstado("CONFIRMADA")).thenReturn(List.of(new Reserva()));
        ResponseEntity<List<Reserva>> response = reservaController.obtenerPorEstado("CONFIRMADA");
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testCrearReserva() {
        // Simula creación exitosa de reserva
        Reserva reserva = new Reserva();
        reserva.setId(1L);
        when(reservaService.guardar(reserva)).thenReturn(reserva);
        ResponseEntity<Reserva> response = reservaController.crearReserva(reserva);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void testActualizarReservaFound() {
        // Simula actualización exitosa de reserva existente
        Reserva existing = new Reserva();
        existing.setId(1L);
        Reserva updated = new Reserva();
        updated.setId(1L);
        when(reservaService.obtenerPorId(1L)).thenReturn(Optional.of(existing));
        when(reservaService.guardar(updated)).thenReturn(updated);
        ResponseEntity<Reserva> response = reservaController.actualizarReserva(1L, updated);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testActualizarReservaNotFound() {
        // Simula intento de actualizar reserva inexistente
        when(reservaService.obtenerPorId(99L)).thenReturn(Optional.empty());
        ResponseEntity<Reserva> response = reservaController.actualizarReserva(99L, new Reserva());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testEliminarReservaFound() {
        // Simula eliminación exitosa de reserva existente
        Reserva reserva = new Reserva();
        reserva.setId(1L);
        when(reservaService.obtenerPorId(1L)).thenReturn(Optional.of(reserva));
        ResponseEntity<Void> response = reservaController.eliminarReserva(1L);
        assertEquals(200, response.getStatusCodeValue());
        verify(reservaService).eliminar(1L);
    }

    @Test
    void testEliminarReservaNotFound() {
        // Simula intento de eliminar reserva inexistente
        when(reservaService.obtenerPorId(99L)).thenReturn(Optional.empty());
        ResponseEntity<Void> response = reservaController.eliminarReserva(99L);
        assertEquals(404, response.getStatusCodeValue());
    }
}

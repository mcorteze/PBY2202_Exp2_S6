package com.letrasypapeles.backend.service;

import com.letrasypapeles.backend.entity.Reserva;
import com.letrasypapeles.backend.repository.ReservaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ReservaServiceTest {

    private ReservaRepository reservaRepository;
    private ReservaService reservaService;

    @BeforeEach
    void setUp() {
        // Se crea el mock del repositorio y se instancia el servicio
        reservaRepository = mock(ReservaRepository.class);
        reservaService = new ReservaService(reservaRepository);
    }

    @Test
    void testObtenerTodas() {
        // Prueba obtener todas las reservas
        when(reservaRepository.findAll()).thenReturn(List.of(new Reserva()));

        List<Reserva> result = reservaService.obtenerTodas();

        assertEquals(1, result.size());
    }

    @Test
    void testObtenerPorId() {
        // Prueba obtener reserva por ID
        Reserva r = new Reserva(1L, null, "CONFIRMADA", null, null);
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(r));

        Optional<Reserva> result = reservaService.obtenerPorId(1L);

        assertTrue(result.isPresent());
        assertEquals("CONFIRMADA", result.get().getEstado());
    }

    @Test
    void testGuardar() {
        // Prueba guardar una reserva
        Reserva r = new Reserva();
        when(reservaRepository.save(r)).thenReturn(r);

        Reserva result = reservaService.guardar(r);

        assertNotNull(result);
        verify(reservaRepository).save(r);
    }

    @Test
    void testEliminar() {
        // Prueba eliminar reserva por ID
        reservaService.eliminar(12L);
        verify(reservaRepository).deleteById(12L);
    }

    @Test
    void testObtenerPorClienteId() {
        // Prueba obtener reservas por ID de cliente
        when(reservaRepository.findByClienteId(5L)).thenReturn(List.of(new Reserva()));

        List<Reserva> result = reservaService.obtenerPorClienteId(5L);

        assertEquals(1, result.size());
    }

    @Test
    void testObtenerPorProductoId() {
        // Prueba obtener reservas por ID de producto
        when(reservaRepository.findByProductoId(8L)).thenReturn(List.of(new Reserva()));

        List<Reserva> result = reservaService.obtenerPorProductoId(8L);

        assertEquals(1, result.size());
    }

    @Test
    void testObtenerPorEstado() {
        // Prueba obtener reservas por estado
        when(reservaRepository.findByEstado("PENDIENTE")).thenReturn(List.of(new Reserva()));

        List<Reserva> result = reservaService.obtenerPorEstado("PENDIENTE");

        assertEquals(1, result.size());
    }
}

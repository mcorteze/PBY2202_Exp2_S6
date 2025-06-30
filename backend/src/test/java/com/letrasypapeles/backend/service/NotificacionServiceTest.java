package com.letrasypapeles.backend.service;

import com.letrasypapeles.backend.entity.Notificacion;
import com.letrasypapeles.backend.repository.NotificacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class NotificacionServiceTest {

    private NotificacionRepository notificacionRepository;
    private NotificacionService notificacionService;

    @BeforeEach
    void setUp() {
        // Se crea el mock del repositorio y se instancia el servicio
        notificacionRepository = mock(NotificacionRepository.class);
        notificacionService = new NotificacionService(notificacionRepository);
    }

    @Test
    void testObtenerTodas() {
        // Prueba obtener todas las notificaciones
        when(notificacionRepository.findAll()).thenReturn(List.of(new Notificacion()));

        List<Notificacion> result = notificacionService.obtenerTodas();

        assertEquals(1, result.size());
    }

    @Test
    void testObtenerPorId() {
        // Prueba obtener notificación por ID
        Notificacion n = new Notificacion(1L, "Mensaje", LocalDateTime.now(), null);
        when(notificacionRepository.findById(1L)).thenReturn(Optional.of(n));

        Optional<Notificacion> result = notificacionService.obtenerPorId(1L);

        assertTrue(result.isPresent());
        assertEquals("Mensaje", result.get().getMensaje());
    }

    @Test
    void testGuardar() {
        // Prueba guardar una notificación
        Notificacion n = new Notificacion();
        when(notificacionRepository.save(n)).thenReturn(n);

        Notificacion result = notificacionService.guardar(n);

        assertNotNull(result);
        verify(notificacionRepository).save(n);
    }

    @Test
    void testEliminar() {
        // Prueba eliminar notificación por ID
        notificacionService.eliminar(5L);
        verify(notificacionRepository).deleteById(5L);
    }

    @Test
    void testObtenerPorClienteId() {
        // Prueba obtener notificaciones por ID de cliente
        when(notificacionRepository.findByClienteId(3L)).thenReturn(List.of(new Notificacion()));

        List<Notificacion> result = notificacionService.obtenerPorClienteId(3L);

        assertEquals(1, result.size());
    }

    @Test
    void testObtenerPorFechaEntre() {
        // Prueba obtener notificaciones entre dos fechas
        LocalDateTime ini = LocalDateTime.now().minusDays(1);
        LocalDateTime fin = LocalDateTime.now();

        when(notificacionRepository.findByFechaBetween(ini, fin)).thenReturn(List.of(new Notificacion()));

        List<Notificacion> result = notificacionService.obtenerPorFechaEntre(ini, fin);

        assertEquals(1, result.size());
    }
}

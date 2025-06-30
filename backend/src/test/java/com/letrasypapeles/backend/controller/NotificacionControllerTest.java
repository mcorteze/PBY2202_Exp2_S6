package com.letrasypapeles.backend.controller;

import com.letrasypapeles.backend.entity.Notificacion;
import com.letrasypapeles.backend.service.NotificacionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class NotificacionControllerTest {

    private NotificacionService notificacionService;
    private NotificacionController notificacionController;

    @BeforeEach
    void setUp() {
        // Se crean los mocks necesarios para el controlador
        notificacionService = mock(NotificacionService.class);
        notificacionController = new NotificacionController(notificacionService);
    }

    @Test
    void testObtenerTodas() {
        // Simula retorno de lista de notificaciones
        when(notificacionService.obtenerTodas())
                .thenReturn(List.of(new Notificacion()));

        ResponseEntity<List<Notificacion>> response = notificacionController.obtenerTodas();

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testObtenerPorIdFound() {
        // Simula búsqueda exitosa por ID
        Notificacion notif = new Notificacion();
        notif.setId(1L);

        when(notificacionService.obtenerPorId(1L))
                .thenReturn(Optional.of(notif));

        ResponseEntity<Notificacion> response = notificacionController.obtenerPorId(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void testObtenerPorIdNotFound() {
        // Simula búsqueda por ID que no encuentra datos
        when(notificacionService.obtenerPorId(99L))
                .thenReturn(Optional.empty());

        ResponseEntity<Notificacion> response = notificacionController.obtenerPorId(99L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testObtenerPorClienteId() {
        // Simula búsqueda de notificaciones por ID de cliente
        when(notificacionService.obtenerPorClienteId(5L))
                .thenReturn(List.of(new Notificacion()));

        ResponseEntity<List<Notificacion>> response = notificacionController.obtenerPorClienteId(5L);

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testCrearNotificacion() {
        // Simula creación exitosa de notificación
        Notificacion notif = new Notificacion();
        notif.setId(1L);

        when(notificacionService.guardar(notif))
                .thenReturn(notif);

        ResponseEntity<Notificacion> response = notificacionController.crearNotificacion(notif);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void testEliminarNotificacionFound() {
        // Simula eliminación exitosa de notificación existente
        Notificacion notif = new Notificacion();
        notif.setId(1L);

        when(notificacionService.obtenerPorId(1L))
                .thenReturn(Optional.of(notif));

        ResponseEntity<Void> response = notificacionController.eliminarNotificacion(1L);

        assertEquals(200, response.getStatusCodeValue());
        verify(notificacionService).eliminar(1L);
    }

    @Test
    void testEliminarNotificacionNotFound() {
        // Simula intento de eliminar notificación inexistente
        when(notificacionService.obtenerPorId(99L))
                .thenReturn(Optional.empty());

        ResponseEntity<Void> response = notificacionController.eliminarNotificacion(99L);

        assertEquals(404, response.getStatusCodeValue());
    }
}

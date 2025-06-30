package com.letrasypapeles.backend.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class NotificacionTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        // Prueba constructor vacío y uso de setters
        Notificacion n = new Notificacion();
        n.setId(1L);
        n.setMensaje("Mensaje de prueba");
        n.setFecha(LocalDateTime.of(2025, 6, 30, 10, 0));
        Cliente cliente = new Cliente();
        n.setCliente(cliente);

        assertEquals(1L, n.getId());
        assertEquals("Mensaje de prueba", n.getMensaje());
        assertEquals(LocalDateTime.of(2025, 6, 30, 10, 0), n.getFecha());
        assertEquals(cliente, n.getCliente());
    }

    @Test
    void testAllArgsConstructor() {
        // Prueba constructor con todos los argumentos
        Cliente cliente = new Cliente();
        LocalDateTime fecha = LocalDateTime.now();

        Notificacion n = new Notificacion(2L, "Notificación", fecha, cliente);

        assertEquals(2L, n.getId());
        assertEquals("Notificación", n.getMensaje());
        assertEquals(fecha, n.getFecha());
        assertEquals(cliente, n.getCliente());
    }

    @Test
    void testBuilder() {
        // Prueba patrón builder
        Cliente cliente = new Cliente();

        Notificacion n = Notificacion.builder()
                .id(3L)
                .mensaje("Con builder")
                .fecha(LocalDateTime.of(2025, 1, 1, 12, 0))
                .cliente(cliente)
                .build();

        assertEquals(3L, n.getId());
        assertEquals("Con builder", n.getMensaje());
        assertEquals(LocalDateTime.of(2025, 1, 1, 12, 0), n.getFecha());
        assertEquals(cliente, n.getCliente());
    }

    @Test
    void testEqualsAndHashCode() {
        // Prueba métodos equals y hashCode
        Notificacion n1 = new Notificacion(1L, "X", LocalDateTime.now(), null);
        Notificacion n2 = new Notificacion(1L, "X", LocalDateTime.now(), null);
        Notificacion n3 = new Notificacion(2L, "Y", LocalDateTime.now(), null);

        assertEquals(n1, n2);
        assertNotEquals(n1, n3);
        assertEquals(n1.hashCode(), n2.hashCode());
    }

    @Test
    void testToString() {
        // Prueba método toString
        Notificacion n = new Notificacion(1L, "Mensaje", LocalDateTime.now(), null);
        assertTrue(n.toString().contains("Mensaje"));
    }
}

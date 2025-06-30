package com.letrasypapeles.backend.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ReservaTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        // Prueba constructor vacío y uso de setters
        Reserva r = new Reserva();
        r.setId(1L);
        r.setFechaReserva(LocalDateTime.of(2025, 7, 10, 14, 0));
        r.setEstado("ACTIVA");
        Cliente cliente = new Cliente();
        Producto producto = new Producto();
        r.setCliente(cliente);
        r.setProducto(producto);

        assertEquals(1L, r.getId());
        assertEquals(LocalDateTime.of(2025, 7, 10, 14, 0), r.getFechaReserva());
        assertEquals("ACTIVA", r.getEstado());
        assertEquals(cliente, r.getCliente());
        assertEquals(producto, r.getProducto());
    }

    @Test
    void testAllArgsConstructor() {
        // Prueba constructor con todos los argumentos
        Cliente cliente = new Cliente();
        Producto producto = new Producto();
        LocalDateTime fecha = LocalDateTime.now();

        Reserva r = new Reserva(2L, fecha, "CANCELADA", cliente, producto);

        assertEquals(2L, r.getId());
        assertEquals(fecha, r.getFechaReserva());
        assertEquals("CANCELADA", r.getEstado());
        assertEquals(cliente, r.getCliente());
        assertEquals(producto, r.getProducto());
    }

    @Test
    void testBuilder() {
        // Prueba patrón builder
        Cliente cliente = new Cliente();
        Producto producto = new Producto();

        Reserva r = Reserva.builder()
                .id(3L)
                .fechaReserva(LocalDateTime.of(2025, 12, 1, 9, 0))
                .estado("FINALIZADA")
                .cliente(cliente)
                .producto(producto)
                .build();

        assertEquals(3L, r.getId());
        assertEquals("FINALIZADA", r.getEstado());
        assertEquals(cliente, r.getCliente());
        assertEquals(producto, r.getProducto());
    }

    @Test
    void testEqualsAndHashCode() {
        // Prueba métodos equals y hashCode
        Reserva r1 = new Reserva(1L, LocalDateTime.now(), "X", null, null);
        Reserva r2 = new Reserva(1L, LocalDateTime.now(), "X", null, null);
        Reserva r3 = new Reserva(2L, LocalDateTime.now(), "Y", null, null);

        assertEquals(r1, r2);
        assertNotEquals(r1, r3);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void testToString() {
        // Prueba método toString
        Reserva r = new Reserva(1L, LocalDateTime.now(), "X", null, null);
        assertTrue(r.toString().contains("X"));
    }
}

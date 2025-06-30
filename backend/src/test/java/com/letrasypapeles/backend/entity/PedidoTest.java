package com.letrasypapeles.backend.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        // Prueba constructor vacío y uso de setters
        Pedido p = new Pedido();
        p.setId(1L);
        p.setFecha(LocalDateTime.of(2025, 5, 10, 12, 30));
        p.setEstado("PENDIENTE");
        Cliente cliente = new Cliente();
        Producto producto = new Producto();
        p.setCliente(cliente);
        p.setListaProductos(List.of(producto));

        assertEquals(1L, p.getId());
        assertEquals(LocalDateTime.of(2025, 5, 10, 12, 30), p.getFecha());
        assertEquals("PENDIENTE", p.getEstado());
        assertEquals(cliente, p.getCliente());
        assertEquals(1, p.getListaProductos().size());
    }

    @Test
    void testAllArgsConstructor() {
        // Prueba constructor con todos los argumentos
        Cliente cliente = new Cliente();
        Producto producto = new Producto();
        LocalDateTime fecha = LocalDateTime.of(2025, 6, 30, 11, 35, 10);

        Pedido p = new Pedido(2L, fecha, "FINALIZADO", cliente, List.of(producto));

        assertEquals(2L, p.getId());
        assertEquals(fecha, p.getFecha());
        assertEquals("FINALIZADO", p.getEstado());
        assertEquals(cliente, p.getCliente());
        assertTrue(p.getListaProductos().contains(producto));
    }

    @Test
    void testBuilder() {
        // Prueba patrón builder
        Cliente cliente = new Cliente();
        Producto producto = new Producto();

        Pedido p = Pedido.builder()
                .id(3L)
                .fecha(LocalDateTime.of(2025, 12, 25, 18, 0))
                .estado("CANCELADO")
                .cliente(cliente)
                .listaProductos(List.of(producto))
                .build();

        assertEquals(3L, p.getId());
        assertEquals("CANCELADO", p.getEstado());
        assertEquals(cliente, p.getCliente());
        assertEquals(1, p.getListaProductos().size());
    }

    @Test
    void testEqualsAndHashCode() {
        // Prueba métodos equals y hashCode
        LocalDateTime fixedDate = LocalDateTime.of(2025, 6, 30, 11, 35, 10);

        Pedido p1 = new Pedido(1L, fixedDate, "X", null, null);
        Pedido p2 = new Pedido(1L, fixedDate, "X", null, null);
        Pedido p3 = new Pedido(2L, fixedDate, "Y", null, null);

        assertEquals(p1, p2);
        assertNotEquals(p1, p3);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testToString() {
        // Prueba método toString
        LocalDateTime fixedDate = LocalDateTime.of(2025, 6, 30, 11, 35, 10);
        Pedido p = new Pedido(1L, fixedDate, "OK", null, null);
        assertTrue(p.toString().contains("OK"));
    }
}

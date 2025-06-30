package com.letrasypapeles.backend.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventarioTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        // Prueba constructor vacío y uso de setters
        Inventario inv = new Inventario();
        inv.setId(1L);
        inv.setCantidad(5);
        inv.setUmbral(2);
        Producto prod = new Producto();
        Sucursal suc = new Sucursal();
        inv.setProducto(prod);
        inv.setSucursal(suc);

        assertEquals(1L, inv.getId());
        assertEquals(5, inv.getCantidad());
        assertEquals(2, inv.getUmbral());
        assertEquals(prod, inv.getProducto());
        assertEquals(suc, inv.getSucursal());
    }

    @Test
    void testAllArgsConstructor() {
        // Prueba constructor con todos los argumentos
        Producto prod = new Producto();
        Sucursal suc = new Sucursal();
        Inventario inv = new Inventario(1L, 10, 3, prod, suc);

        assertEquals(1L, inv.getId());
        assertEquals(10, inv.getCantidad());
        assertEquals(3, inv.getUmbral());
        assertEquals(prod, inv.getProducto());
        assertEquals(suc, inv.getSucursal());
    }

    @Test
    void testBuilder() {
        // Prueba patrón builder
        Producto prod = new Producto();
        Sucursal suc = new Sucursal();

        Inventario inv = Inventario.builder()
                .id(2L)
                .cantidad(15)
                .umbral(5)
                .producto(prod)
                .sucursal(suc)
                .build();

        assertEquals(2L, inv.getId());
        assertEquals(15, inv.getCantidad());
        assertEquals(5, inv.getUmbral());
        assertEquals(prod, inv.getProducto());
        assertEquals(suc, inv.getSucursal());
    }

    @Test
    void testEqualsAndHashCode() {
        // Prueba métodos equals y hashCode
        Producto prod = new Producto();
        Sucursal suc = new Sucursal();

        Inventario i1 = new Inventario(1L, 10, 3, prod, suc);
        Inventario i2 = new Inventario(1L, 10, 3, prod, suc);
        Inventario i3 = new Inventario(2L, 5, 2, null, null);

        assertEquals(i1, i2);
        assertNotEquals(i1, i3);
        assertEquals(i1.hashCode(), i2.hashCode());
        assertNotEquals(i1.hashCode(), i3.hashCode());
    }

    @Test
    void testToString() {
        // Prueba método toString
        Inventario inv = new Inventario(1L, 10, 3, null, null);
        assertTrue(inv.toString().contains("10"));
    }
}

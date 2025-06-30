package com.letrasypapeles.backend.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductoTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        // Prueba constructor vacío y uso de setters
        Producto prod = new Producto();
        prod.setId(1L);
        prod.setNombre("Lapicera");
        prod.setDescripcion("Azul");
        prod.setPrecio(BigDecimal.valueOf(2.5));
        prod.setStock(50);
        Categoria cat = new Categoria();
        Proveedor prov = new Proveedor();
        prod.setCategoria(cat);
        prod.setProveedor(prov);

        assertEquals(1L, prod.getId());
        assertEquals("Lapicera", prod.getNombre());
        assertEquals("Azul", prod.getDescripcion());
        assertEquals(BigDecimal.valueOf(2.5), prod.getPrecio());
        assertEquals(50, prod.getStock());
        assertEquals(cat, prod.getCategoria());
        assertEquals(prov, prod.getProveedor());
    }

    @Test
    void testAllArgsConstructor() {
        // Prueba constructor con todos los argumentos
        Categoria cat = new Categoria();
        Proveedor prov = new Proveedor();

        Producto prod = new Producto(2L, "Cuaderno", "Rayado",
                BigDecimal.valueOf(3.75), 100, cat, prov);

        assertEquals(2L, prod.getId());
        assertEquals("Cuaderno", prod.getNombre());
        assertEquals("Rayado", prod.getDescripcion());
        assertEquals(BigDecimal.valueOf(3.75), prod.getPrecio());
        assertEquals(100, prod.getStock());
        assertEquals(cat, prod.getCategoria());
        assertEquals(prov, prod.getProveedor());
    }

    @Test
    void testBuilder() {
        // Prueba patrón builder
        Categoria cat = new Categoria();
        Proveedor prov = new Proveedor();

        Producto prod = Producto.builder()
                .id(3L)
                .nombre("Tijeras")
                .descripcion("Acero inoxidable")
                .precio(BigDecimal.valueOf(4.99))
                .stock(20)
                .categoria(cat)
                .proveedor(prov)
                .build();

        assertEquals(3L, prod.getId());
        assertEquals("Tijeras", prod.getNombre());
        assertEquals("Acero inoxidable", prod.getDescripcion());
        assertEquals(BigDecimal.valueOf(4.99), prod.getPrecio());
        assertEquals(20, prod.getStock());
        assertEquals(cat, prod.getCategoria());
        assertEquals(prov, prod.getProveedor());
    }

    @Test
    void testEqualsAndHashCode() {
        // Prueba métodos equals y hashCode
        Producto p1 = new Producto(1L, "X", "Desc", BigDecimal.ONE, 5, null, null);
        Producto p2 = new Producto(1L, "X", "Desc", BigDecimal.ONE, 5, null, null);
        Producto p3 = new Producto(2L, "Y", "Otra", BigDecimal.TEN, 15, null, null);

        assertEquals(p1, p2);
        assertNotEquals(p1, p3);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testToString() {
        // Prueba método toString
        Producto p = new Producto(1L, "Lápiz", "Gráfico", BigDecimal.TEN, 15, null, null);
        assertTrue(p.toString().contains("Lápiz"));
    }
}

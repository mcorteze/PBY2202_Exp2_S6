package com.letrasypapeles.backend.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProveedorTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        // Prueba constructor vacío y uso de setters
        Proveedor prov = new Proveedor();
        prov.setId(1L);
        prov.setNombre("Papelería S.A.");
        prov.setContacto("contacto@papeleria.com");

        assertEquals(1L, prov.getId());
        assertEquals("Papelería S.A.", prov.getNombre());
        assertEquals("contacto@papeleria.com", prov.getContacto());
    }

    @Test
    void testAllArgsConstructor() {
        // Prueba constructor con todos los argumentos
        Proveedor prov = new Proveedor(2L, "Distribuidora", "info@distri.com");
        assertEquals(2L, prov.getId());
        assertEquals("Distribuidora", prov.getNombre());
        assertEquals("info@distri.com", prov.getContacto());
    }

    @Test
    void testBuilder() {
        // Prueba patrón builder
        Proveedor prov = Proveedor.builder()
                .id(3L)
                .nombre("Librería XYZ")
                .contacto("xyz@libreria.com")
                .build();

        assertEquals(3L, prov.getId());
        assertEquals("Librería XYZ", prov.getNombre());
        assertEquals("xyz@libreria.com", prov.getContacto());
    }

    @Test
    void testEqualsAndHashCode() {
        // Prueba métodos equals y hashCode
        Proveedor p1 = new Proveedor(1L, "A", "a@mail.com");
        Proveedor p2 = new Proveedor(1L, "A", "a@mail.com");
        Proveedor p3 = new Proveedor(2L, "B", "b@mail.com");

        assertEquals(p1, p2);
        assertNotEquals(p1, p3);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void testToString() {
        // Prueba método toString
        Proveedor p = new Proveedor(1L, "A", "a@mail.com");
        assertTrue(p.toString().contains("A"));
    }
}

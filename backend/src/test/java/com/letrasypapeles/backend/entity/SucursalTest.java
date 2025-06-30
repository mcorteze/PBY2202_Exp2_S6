package com.letrasypapeles.backend.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SucursalTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        // Prueba constructor vacío y uso de setters
        Sucursal s = new Sucursal();
        s.setId(1L);
        s.setNombre("Sucursal Central");
        s.setDireccion("Av. Principal 123");
        s.setRegion("Región Metropolitana");

        assertEquals(1L, s.getId());
        assertEquals("Sucursal Central", s.getNombre());
        assertEquals("Av. Principal 123", s.getDireccion());
        assertEquals("Región Metropolitana", s.getRegion());
    }

    @Test
    void testAllArgsConstructor() {
        // Prueba constructor con todos los argumentos
        Sucursal s = new Sucursal(2L, "Sucursal Norte", "Calle Norte 456", "Región Norte");

        assertEquals(2L, s.getId());
        assertEquals("Sucursal Norte", s.getNombre());
        assertEquals("Calle Norte 456", s.getDireccion());
        assertEquals("Región Norte", s.getRegion());
    }

    @Test
    void testBuilder() {
        // Prueba patrón builder
        Sucursal s = Sucursal.builder()
                .id(3L)
                .nombre("Sucursal Sur")
                .direccion("Calle Sur 789")
                .region("Región Sur")
                .build();

        assertEquals(3L, s.getId());
        assertEquals("Sucursal Sur", s.getNombre());
        assertEquals("Calle Sur 789", s.getDireccion());
        assertEquals("Región Sur", s.getRegion());
    }

    @Test
    void testEqualsAndHashCode() {
        // Prueba métodos equals y hashCode
        Sucursal s1 = new Sucursal(1L, "A", "Dir", "Región");
        Sucursal s2 = new Sucursal(1L, "A", "Dir", "Región");
        Sucursal s3 = new Sucursal(2L, "B", "Otra", "Otra");

        assertEquals(s1, s2);
        assertNotEquals(s1, s3);
        assertEquals(s1.hashCode(), s2.hashCode());
    }

    @Test
    void testToString() {
        // Prueba método toString
        Sucursal s = new Sucursal(1L, "Sucursal", "Dir", "Región");
        assertTrue(s.toString().contains("Sucursal"));
    }
}

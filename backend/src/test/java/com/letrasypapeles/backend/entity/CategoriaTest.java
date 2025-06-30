package com.letrasypapeles.backend.entity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CategoriaTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        // Prueba constructor vacío y setters
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNombre("Libros");

        assertEquals(1L, categoria.getId());
        assertEquals("Libros", categoria.getNombre());
    }

    @Test
    void testAllArgsConstructor() {
        // Prueba constructor con todos los argumentos
        Categoria categoria = new Categoria(1L, "Papelería");
        assertEquals(1L, categoria.getId());
        assertEquals("Papelería", categoria.getNombre());
    }

    @Test
    void testBuilder() {
        // Prueba patrón builder
        Categoria categoria = Categoria.builder()
                .id(2L)
                .nombre("Computación")
                .build();

        assertEquals(2L, categoria.getId());
        assertEquals("Computación", categoria.getNombre());
    }

    @Test
    void testEqualsAndHashCode() {
        // Prueba equals y hashCode
        Categoria c1 = new Categoria(1L, "X");
        Categoria c2 = new Categoria(1L, "X");
        Categoria c3 = new Categoria(2L, "Y");

        assertEquals(c1, c2);
        assertNotEquals(c1, c3);
        assertEquals(c1.hashCode(), c2.hashCode());
        assertNotEquals(c1.hashCode(), c3.hashCode());
    }

    @Test
    void testToString() {
        // Prueba método toString
        Categoria c = new Categoria(1L, "X");
        assertTrue(c.toString().contains("X"));
    }
}

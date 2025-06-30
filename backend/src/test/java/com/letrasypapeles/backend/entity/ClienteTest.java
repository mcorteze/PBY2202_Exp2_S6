package com.letrasypapeles.backend.entity;

import org.junit.jupiter.api.Test;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        // Prueba constructor vacío y uso de setters
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Manuel");
        cliente.setEmail("manuel@test.com");
        cliente.setContraseña("1234");
        cliente.setPuntosFidelidad(10);
        cliente.setRoles(Set.of(new Role("ADMIN")));

        assertEquals(1L, cliente.getId());
        assertEquals("Manuel", cliente.getNombre());
        assertEquals("manuel@test.com", cliente.getEmail());
        assertEquals("1234", cliente.getContraseña());
        assertEquals(10, cliente.getPuntosFidelidad());
        assertEquals(1, cliente.getRoles().size());
    }

    @Test
    void testAllArgsConstructor() {
        // Prueba constructor con todos los argumentos
        Role role = new Role("CLIENTE");
        Cliente cliente = new Cliente(2L, "Ana", "ana@test.com", "pass", 5, Set.of(role));

        assertEquals(2L, cliente.getId());
        assertEquals("Ana", cliente.getNombre());
        assertEquals("ana@test.com", cliente.getEmail());
        assertEquals("pass", cliente.getContraseña());
        assertEquals(5, cliente.getPuntosFidelidad());
        assertTrue(cliente.getRoles().contains(role));
    }

    @Test
    void testBuilder() {
        // Prueba patrón builder
        Cliente cliente = Cliente.builder()
                .id(3L)
                .nombre("Pedro")
                .email("pedro@test.com")
                .contraseña("pwd")
                .puntosFidelidad(20)
                .roles(Set.of(new Role("GERENTE")))
                .build();

        assertEquals(3L, cliente.getId());
        assertEquals("Pedro", cliente.getNombre());
        assertEquals("pedro@test.com", cliente.getEmail());
        assertEquals("pwd", cliente.getContraseña());
        assertEquals(20, cliente.getPuntosFidelidad());
        assertEquals(1, cliente.getRoles().size());
    }

    @Test
    void testEqualsAndHashCode() {
        // Prueba equals y hashCode
        Cliente c1 = new Cliente(1L, "X", "x@test.com", "pwd", 10, null);
        Cliente c2 = new Cliente(1L, "X", "x@test.com", "pwd", 10, null);
        Cliente c3 = new Cliente(2L, "Y", "y@test.com", "pwd", 5, null);

        assertEquals(c1, c2);
        assertNotEquals(c1, c3);
        assertEquals(c1.hashCode(), c2.hashCode());
        assertNotEquals(c1.hashCode(), c3.hashCode());
    }

    @Test
    void testToString() {
        // Prueba método toString
        Cliente c = new Cliente(1L, "Manuel", "m@test.com", "pwd", 10, null);
        assertTrue(c.toString().contains("Manuel"));
    }
}

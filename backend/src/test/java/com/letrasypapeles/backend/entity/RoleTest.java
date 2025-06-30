package com.letrasypapeles.backend.entity;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        // Prueba constructor vacío y uso de setters
        Role role = new Role();
        role.setNombre("ADMIN");
        role.setClientes(Set.of(new Cliente()));

        assertEquals("ADMIN", role.getNombre());
        assertEquals(1, role.getClientes().size());
    }

    @Test
    void testAllArgsConstructor() {
        // Prueba constructor con todos los argumentos
        Cliente cliente = new Cliente();
        Role role = new Role("GERENTE", Set.of(cliente));

        assertEquals("GERENTE", role.getNombre());
        assertTrue(role.getClientes().contains(cliente));
    }

    @Test
    void testCustomConstructor() {
        // Prueba constructor personalizado con solo nombre
        Role role = new Role("EMPLEADO");
        assertEquals("EMPLEADO", role.getNombre());
        assertNull(role.getClientes());
    }

    @Test
    void testBuilder() {
        // Prueba patrón builder
        Cliente cliente = new Cliente();

        Role role = Role.builder()
                .nombre("CLIENTE")
                .clientes(Set.of(cliente))
                .build();

        assertEquals("CLIENTE", role.getNombre());
        assertEquals(1, role.getClientes().size());
    }

    @Test
    void testEqualsAndHashCode() {
        // Prueba métodos equals y hashCode
        Role r1 = new Role("ADMIN", null);
        Role r2 = new Role("ADMIN", null);
        Role r3 = new Role("OTHER", null);

        assertEquals(r1, r2);
        assertNotEquals(r1, r3);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    @Test
    void testToString() {
        // Prueba método toString
        Role r = new Role("ADMIN", null);
        assertTrue(r.toString().contains("ADMIN"));
    }
}

package com.letrasypapeles.backend.service;

import com.letrasypapeles.backend.entity.Role;
import com.letrasypapeles.backend.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RoleServiceTest {

    private RoleRepository roleRepository;
    private RoleService roleService;

    @BeforeEach
    void setUp() {
        // Se crea el mock del repositorio y se instancia el servicio
        roleRepository = mock(RoleRepository.class);
        roleService = new RoleService(roleRepository);
    }

    @Test
    void testObtenerTodos() {
        // Prueba obtener todos los roles
        when(roleRepository.findAll()).thenReturn(List.of(new Role()));

        List<Role> result = roleService.obtenerTodos();

        assertEquals(1, result.size());
    }

    @Test
    void testObtenerPorNombre() {
        // Prueba obtener rol por nombre
        Role r = new Role("ADMIN");
        when(roleRepository.findByNombre("ADMIN")).thenReturn(Optional.of(r));

        Optional<Role> result = roleService.obtenerPorNombre("ADMIN");

        assertTrue(result.isPresent());
        assertEquals("ADMIN", result.get().getNombre());
    }

    @Test
    void testGuardar() {
        // Prueba guardar un rol
        Role r = new Role();
        when(roleRepository.save(r)).thenReturn(r);

        Role result = roleService.guardar(r);

        assertNotNull(result);
        verify(roleRepository).save(r);
    }

    @Test
    void testEliminar() {
        // Prueba eliminar un rol por nombre (ID)
        roleService.eliminar("CLIENTE");
        verify(roleRepository).deleteById("CLIENTE");
    }
}

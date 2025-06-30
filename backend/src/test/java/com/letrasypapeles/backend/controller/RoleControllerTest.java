package com.letrasypapeles.backend.controller;

import com.letrasypapeles.backend.entity.Role;
import com.letrasypapeles.backend.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RoleControllerTest {

    private RoleService roleService;
    private RoleController roleController;

    @BeforeEach
    void setUp() {
        // Se crean los mocks necesarios para el controlador
        roleService = mock(RoleService.class);
        roleController = new RoleController(roleService);
    }

    @Test
    void testObtenerTodos() {
        // Simula retorno de lista de roles
        when(roleService.obtenerTodos()).thenReturn(List.of(new Role("ADMIN")));
        ResponseEntity<List<Role>> response = roleController.obtenerTodos();
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testObtenerPorNombreFound() {
        // Simula búsqueda exitosa de un rol por nombre
        Role role = new Role("ADMIN");
        when(roleService.obtenerPorNombre("ADMIN")).thenReturn(Optional.of(role));
        ResponseEntity<Role> response = roleController.obtenerPorNombre("ADMIN");
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("ADMIN", response.getBody().getNombre());
    }

    @Test
    void testObtenerPorNombreNotFound() {
        // Simula búsqueda de rol que no existe
        when(roleService.obtenerPorNombre("FAKE")).thenReturn(Optional.empty());
        ResponseEntity<Role> response = roleController.obtenerPorNombre("FAKE");
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCrearRole() {
        // Simula creación exitosa de rol
        Role role = new Role("NEW_ROLE");
        when(roleService.guardar(role)).thenReturn(role);
        ResponseEntity<Role> response = roleController.crearRole(role);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("NEW_ROLE", response.getBody().getNombre());
    }

    @Test
    void testEliminarRoleFound() {
        // Simula eliminación exitosa de rol existente
        Role role = new Role("ADMIN");
        when(roleService.obtenerPorNombre("ADMIN")).thenReturn(Optional.of(role));
        ResponseEntity<Void> response = roleController.eliminarRole("ADMIN");
        assertEquals(200, response.getStatusCodeValue());
        verify(roleService).eliminar("ADMIN");
    }

    @Test
    void testEliminarRoleNotFound() {
        // Simula intento de eliminar rol inexistente
        when(roleService.obtenerPorNombre("FAKE")).thenReturn(Optional.empty());
        ResponseEntity<Void> response = roleController.eliminarRole("FAKE");
        assertEquals(404, response.getStatusCodeValue());
    }
}

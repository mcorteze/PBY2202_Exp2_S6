package com.letrasypapeles.backend.controller;

import com.letrasypapeles.backend.entity.Inventario;
import com.letrasypapeles.backend.service.InventarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class InventarioControllerTest {

    private InventarioService inventarioService;
    private InventarioController inventarioController;

    @BeforeEach
    void setUp() {
        // Se crean los mocks necesarios para el controlador
        inventarioService = mock(InventarioService.class);
        inventarioController = new InventarioController(inventarioService);
    }

    @Test
    void testObtenerTodos() {
        // Simula retorno de lista de inventarios
        when(inventarioService.obtenerTodos()).thenReturn(List.of(new Inventario()));

        ResponseEntity<List<Inventario>> response = inventarioController.obtenerTodos();

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testObtenerPorIdFound() {
        // Simula búsqueda exitosa por ID
        Inventario inv = new Inventario();
        inv.setId(1L);

        when(inventarioService.obtenerPorId(1L)).thenReturn(Optional.of(inv));

        ResponseEntity<Inventario> response = inventarioController.obtenerPorId(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void testObtenerPorIdNotFound() {
        // Simula búsqueda por ID que no encuentra datos
        when(inventarioService.obtenerPorId(99L)).thenReturn(Optional.empty());

        ResponseEntity<Inventario> response = inventarioController.obtenerPorId(99L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testObtenerPorProductoId() {
        // Simula búsqueda de inventarios por ID de producto
        when(inventarioService.obtenerPorProductoId(10L))
                .thenReturn(List.of(new Inventario()));

        ResponseEntity<List<Inventario>> response = inventarioController.obtenerPorProductoId(10L);

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testObtenerPorSucursalId() {
        // Simula búsqueda de inventarios por ID de sucursal
        when(inventarioService.obtenerPorSucursalId(20L))
                .thenReturn(List.of(new Inventario()));

        ResponseEntity<List<Inventario>> response = inventarioController.obtenerPorSucursalId(20L);

        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testCrearInventario() {
        // Simula creación exitosa de inventario
        Inventario inv = new Inventario();
        inv.setId(1L);

        when(inventarioService.guardar(inv)).thenReturn(inv);

        ResponseEntity<Inventario> response = inventarioController.crearInventario(inv);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void testActualizarInventarioFound() {
        // Simula actualización exitosa de inventario existente
        Inventario existing = new Inventario();
        existing.setId(1L);

        Inventario updated = new Inventario();
        updated.setId(1L);

        when(inventarioService.obtenerPorId(1L)).thenReturn(Optional.of(existing));
        when(inventarioService.guardar(updated)).thenReturn(updated);

        ResponseEntity<Inventario> response = inventarioController.actualizarInventario(1L, updated);

        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testActualizarInventarioNotFound() {
        // Simula intento de actualizar inventario inexistente
        when(inventarioService.obtenerPorId(99L)).thenReturn(Optional.empty());

        ResponseEntity<Inventario> response = inventarioController.actualizarInventario(99L, new Inventario());

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testEliminarInventarioFound() {
        // Simula eliminación exitosa de inventario existente
        Inventario inv = new Inventario();
        inv.setId(1L);

        when(inventarioService.obtenerPorId(1L)).thenReturn(Optional.of(inv));

        ResponseEntity<Void> response = inventarioController.eliminarInventario(1L);

        assertEquals(200, response.getStatusCodeValue());
        verify(inventarioService).eliminar(1L);
    }

    @Test
    void testEliminarInventarioNotFound() {
        // Simula intento de eliminar inventario inexistente
        when(inventarioService.obtenerPorId(99L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = inventarioController.eliminarInventario(99L);

        assertEquals(404, response.getStatusCodeValue());
    }
}

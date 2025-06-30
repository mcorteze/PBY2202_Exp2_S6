package com.letrasypapeles.backend.controller;

import com.letrasypapeles.backend.entity.Sucursal;
import com.letrasypapeles.backend.service.SucursalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SucursalControllerTest {

    private SucursalService sucursalService;
    private SucursalController sucursalController;

    @BeforeEach
    void setUp() {
        // Se crean los mocks necesarios para el controlador
        sucursalService = mock(SucursalService.class);
        sucursalController = new SucursalController(sucursalService);
    }

    @Test
    void testObtenerTodas() {
        // Simula retorno de lista de sucursales
        when(sucursalService.obtenerTodas()).thenReturn(List.of(new Sucursal()));
        ResponseEntity<List<Sucursal>> response = sucursalController.obtenerTodas();
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testObtenerPorIdFound() {
        // Simula búsqueda exitosa por ID
        Sucursal suc = new Sucursal(1L, "Sucursal", "Dirección", "Región");
        when(sucursalService.obtenerPorId(1L)).thenReturn(Optional.of(suc));
        ResponseEntity<Sucursal> response = sucursalController.obtenerPorId(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Sucursal", response.getBody().getNombre());
    }

    @Test
    void testObtenerPorIdNotFound() {
        // Simula búsqueda por ID que no encuentra datos
        when(sucursalService.obtenerPorId(99L)).thenReturn(Optional.empty());
        ResponseEntity<Sucursal> response = sucursalController.obtenerPorId(99L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCrearSucursal() {
        // Simula creación exitosa de sucursal
        Sucursal suc = new Sucursal(null, "Nueva", "Dir", "Región");
        when(sucursalService.guardar(suc)).thenReturn(suc);
        ResponseEntity<Sucursal> response = sucursalController.crearSucursal(suc);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Nueva", response.getBody().getNombre());
    }

    @Test
    void testActualizarSucursalFound() {
        // Simula actualización exitosa de sucursal existente
        Sucursal suc = new Sucursal(1L, "Old", "Dir", "Región");
        Sucursal updated = new Sucursal(1L, "Updated", "Dir", "Región");

        when(sucursalService.obtenerPorId(1L)).thenReturn(Optional.of(suc));
        when(sucursalService.guardar(updated)).thenReturn(updated);

        ResponseEntity<Sucursal> response = sucursalController.actualizarSucursal(1L, updated);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Updated", response.getBody().getNombre());
    }

    @Test
    void testActualizarSucursalNotFound() {
        // Simula intento de actualizar sucursal inexistente
        when(sucursalService.obtenerPorId(99L)).thenReturn(Optional.empty());
        ResponseEntity<Sucursal> response = sucursalController.actualizarSucursal(99L, new Sucursal());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testEliminarSucursalFound() {
        // Simula eliminación exitosa de sucursal existente
        Sucursal suc = new Sucursal(1L, "Sucursal", "Dir", "Región");
        when(sucursalService.obtenerPorId(1L)).thenReturn(Optional.of(suc));
        ResponseEntity<Void> response = sucursalController.eliminarSucursal(1L);
        assertEquals(200, response.getStatusCodeValue());
        verify(sucursalService).eliminar(1L);
    }

    @Test
    void testEliminarSucursalNotFound() {
        // Simula intento de eliminar sucursal inexistente
        when(sucursalService.obtenerPorId(99L)).thenReturn(Optional.empty());
        ResponseEntity<Void> response = sucursalController.eliminarSucursal(99L);
        assertEquals(404, response.getStatusCodeValue());
    }
}

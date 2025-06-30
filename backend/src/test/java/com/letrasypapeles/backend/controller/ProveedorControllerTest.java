package com.letrasypapeles.backend.controller;

import com.letrasypapeles.backend.entity.Proveedor;
import com.letrasypapeles.backend.service.ProveedorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ProveedorControllerTest {

    private ProveedorService proveedorService;
    private ProveedorController proveedorController;

    @BeforeEach
    void setUp() {
        // Se crean los mocks necesarios para el controlador
        proveedorService = mock(ProveedorService.class);
        proveedorController = new ProveedorController(proveedorService);
    }

    @Test
    void testObtenerTodos() {
        // Simula retorno de lista de proveedores
        when(proveedorService.obtenerTodos()).thenReturn(List.of(new Proveedor()));
        ResponseEntity<List<Proveedor>> response = proveedorController.obtenerTodos();
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testObtenerPorIdFound() {
        // Simula búsqueda exitosa por ID
        Proveedor proveedor = new Proveedor(1L, "Proveedor", "Contacto");
        when(proveedorService.obtenerPorId(1L)).thenReturn(Optional.of(proveedor));
        ResponseEntity<Proveedor> response = proveedorController.obtenerPorId(1L);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Proveedor", response.getBody().getNombre());
    }

    @Test
    void testObtenerPorIdNotFound() {
        // Simula búsqueda por ID que no encuentra datos
        when(proveedorService.obtenerPorId(99L)).thenReturn(Optional.empty());
        ResponseEntity<Proveedor> response = proveedorController.obtenerPorId(99L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCrearProveedor() {
        // Simula creación exitosa de proveedor
        Proveedor proveedor = new Proveedor(null, "Nuevo", "Contacto");
        when(proveedorService.guardar(proveedor)).thenReturn(proveedor);
        ResponseEntity<Proveedor> response = proveedorController.crearProveedor(proveedor);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Nuevo", response.getBody().getNombre());
    }

    @Test
    void testActualizarProveedorFound() {
        // Simula actualización exitosa de proveedor existente
        Proveedor proveedor = new Proveedor(1L, "Viejo", "Contacto");
        Proveedor actualizado = new Proveedor(1L, "Actualizado", "Contacto");

        when(proveedorService.obtenerPorId(1L)).thenReturn(Optional.of(proveedor));
        when(proveedorService.guardar(actualizado)).thenReturn(actualizado);

        ResponseEntity<Proveedor> response = proveedorController.actualizarProveedor(1L, actualizado);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Actualizado", response.getBody().getNombre());
    }

    @Test
    void testActualizarProveedorNotFound() {
        // Simula intento de actualizar proveedor inexistente
        when(proveedorService.obtenerPorId(99L)).thenReturn(Optional.empty());
        ResponseEntity<Proveedor> response = proveedorController.actualizarProveedor(99L, new Proveedor());
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testEliminarProveedorFound() {
        // Simula eliminación exitosa de proveedor existente
        Proveedor proveedor = new Proveedor(1L, "Proveedor", "Contacto");
        when(proveedorService.obtenerPorId(1L)).thenReturn(Optional.of(proveedor));
        ResponseEntity<Void> response = proveedorController.eliminarProveedor(1L);
        assertEquals(200, response.getStatusCodeValue());
        verify(proveedorService).eliminar(1L);
    }

    @Test
    void testEliminarProveedorNotFound() {
        // Simula intento de eliminar proveedor inexistente
        when(proveedorService.obtenerPorId(99L)).thenReturn(Optional.empty());
        ResponseEntity<Void> response = proveedorController.eliminarProveedor(99L);
        assertEquals(404, response.getStatusCodeValue());
    }
}

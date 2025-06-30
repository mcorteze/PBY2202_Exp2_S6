package com.letrasypapeles.backend.controller;

import com.letrasypapeles.backend.entity.Producto;
import com.letrasypapeles.backend.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoControllerTest {

    private ProductoService productoService;
    private ProductoController productoController;

    @BeforeEach
    void setUp() {
        // Se crean los mocks necesarios para el controlador
        productoService = mock(ProductoService.class);
        productoController = new ProductoController(productoService);
    }

    @Test
    void testObtenerTodos() {
        // Simula retorno de lista de productos
        when(productoService.obtenerTodos()).thenReturn(List.of(new Producto()));
        var response = productoController.obtenerTodos();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testObtenerPorIdFound() {
        // Simula búsqueda exitosa por ID
        Producto producto = new Producto();
        when(productoService.obtenerPorId(1L)).thenReturn(Optional.of(producto));

        var response = productoController.obtenerPorId(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(producto, response.getBody());
    }

    @Test
    void testObtenerPorIdNotFound() {
        // Simula búsqueda por ID que no encuentra datos
        when(productoService.obtenerPorId(1L)).thenReturn(Optional.empty());

        var response = productoController.obtenerPorId(1L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCrearProducto() {
        // Simula creación exitosa de producto
        Producto producto = new Producto();
        when(productoService.guardar(producto)).thenReturn(producto);

        var response = productoController.crearProducto(producto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(producto, response.getBody());
    }

    @Test
    void testActualizarProductoFound() {
        // Simula actualización exitosa de producto existente
        Producto producto = new Producto();
        producto.setNombre("Original");

        Producto actualizado = new Producto();
        actualizado.setNombre("Actualizado");

        when(productoService.obtenerPorId(1L)).thenReturn(Optional.of(producto));
        when(productoService.guardar(any(Producto.class))).thenReturn(actualizado);

        var response = productoController.actualizarProducto(1L, producto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Actualizado", response.getBody().getNombre());

        // Verifica que el ID se establezca correctamente
        ArgumentCaptor<Producto> captor = ArgumentCaptor.forClass(Producto.class);
        verify(productoService).guardar(captor.capture());
        assertEquals(1L, captor.getValue().getId());
    }

    @Test
    void testActualizarProductoNotFound() {
        // Simula intento de actualizar producto inexistente
        when(productoService.obtenerPorId(1L)).thenReturn(Optional.empty());

        var response = productoController.actualizarProducto(1L, new Producto());

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testEliminarProductoFound() {
        // Simula eliminación exitosa de producto existente
        Producto producto = new Producto();
        when(productoService.obtenerPorId(1L)).thenReturn(Optional.of(producto));

        var response = productoController.eliminarProducto(1L);

        assertEquals(200, response.getStatusCodeValue());
        verify(productoService).eliminar(1L);
    }

    @Test
    void testEliminarProductoNotFound() {
        // Simula intento de eliminar producto inexistente
        when(productoService.obtenerPorId(1L)).thenReturn(Optional.empty());

        var response = productoController.eliminarProducto(1L);

        assertEquals(404, response.getStatusCodeValue());
        verify(productoService, never()).eliminar(any());
    }
}

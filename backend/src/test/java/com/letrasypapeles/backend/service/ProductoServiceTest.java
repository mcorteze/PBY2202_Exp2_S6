package com.letrasypapeles.backend.service;

import com.letrasypapeles.backend.entity.Producto;
import com.letrasypapeles.backend.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductoServiceTest {

    private ProductoRepository productoRepository;
    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        // Se crea el mock del repositorio y se instancia el servicio
        productoRepository = mock(ProductoRepository.class);
        productoService = new ProductoService(productoRepository);
    }

    @Test
    void testObtenerTodos() {
        // Prueba obtener todos los productos
        when(productoRepository.findAll()).thenReturn(List.of(new Producto()));

        List<Producto> result = productoService.obtenerTodos();

        assertEquals(1, result.size());
    }

    @Test
    void testObtenerPorId() {
        // Prueba obtener producto por ID
        Producto p = new Producto(1L, "Libro", "Desc", null, 10, null, null);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(p));

        Optional<Producto> result = productoService.obtenerPorId(1L);

        assertTrue(result.isPresent());
        assertEquals("Libro", result.get().getNombre());
    }

    @Test
    void testGuardar() {
        // Prueba guardar un producto
        Producto p = new Producto();
        when(productoRepository.save(p)).thenReturn(p);

        Producto result = productoService.guardar(p);

        assertNotNull(result);
        verify(productoRepository).save(p);
    }

    @Test
    void testEliminar() {
        // Prueba eliminar producto por ID
        productoService.eliminar(22L);
        verify(productoRepository).deleteById(22L);
    }
}

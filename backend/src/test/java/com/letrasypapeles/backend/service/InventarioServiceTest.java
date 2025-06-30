package com.letrasypapeles.backend.service;

import com.letrasypapeles.backend.entity.Inventario;
import com.letrasypapeles.backend.repository.InventarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class InventarioServiceTest {

    private InventarioRepository inventarioRepository;
    private InventarioService inventarioService;

    @BeforeEach
    void setUp() {
        // Se crea el mock del repositorio y se instancia el servicio
        inventarioRepository = mock(InventarioRepository.class);
        inventarioService = new InventarioService(inventarioRepository);
    }

    @Test
    void testObtenerTodos() {
        // Prueba obtener todos los registros de inventario
        when(inventarioRepository.findAll()).thenReturn(List.of(new Inventario()));

        List<Inventario> result = inventarioService.obtenerTodos();

        assertEquals(1, result.size());
    }

    @Test
    void testObtenerPorId() {
        // Prueba obtener inventario por ID
        Inventario inv = new Inventario(1L, 5, 2, null, null);
        when(inventarioRepository.findById(1L)).thenReturn(Optional.of(inv));

        Optional<Inventario> result = inventarioService.obtenerPorId(1L);

        assertTrue(result.isPresent());
        assertEquals(5, result.get().getCantidad());
    }

    @Test
    void testGuardar() {
        // Prueba guardar un inventario
        Inventario inv = new Inventario();
        when(inventarioRepository.save(inv)).thenReturn(inv);

        Inventario result = inventarioService.guardar(inv);

        assertNotNull(result);
        verify(inventarioRepository, times(1)).save(inv);
    }

    @Test
    void testEliminar() {
        // Prueba eliminar inventario por ID
        inventarioService.eliminar(10L);
        verify(inventarioRepository, times(1)).deleteById(10L);
    }

    @Test
    void testObtenerPorProductoId() {
        // Prueba obtener inventarios por ID de producto
        when(inventarioRepository.findByProductoId(3L)).thenReturn(List.of(new Inventario()));

        List<Inventario> result = inventarioService.obtenerPorProductoId(3L);

        assertEquals(1, result.size());
    }

    @Test
    void testObtenerPorSucursalId() {
        // Prueba obtener inventarios por ID de sucursal
        when(inventarioRepository.findBySucursalId(2L)).thenReturn(List.of(new Inventario()));

        List<Inventario> result = inventarioService.obtenerPorSucursalId(2L);

        assertEquals(1, result.size());
    }

    @Test
    void testObtenerInventarioBajoUmbral() {
        // Prueba obtener inventarios con cantidad menor al umbral
        when(inventarioRepository.findByCantidadLessThan(10)).thenReturn(List.of(new Inventario()));

        List<Inventario> result = inventarioService.obtenerInventarioBajoUmbral(10);

        assertEquals(1, result.size());
    }
}

package com.letrasypapeles.backend.service;

import com.letrasypapeles.backend.entity.Proveedor;
import com.letrasypapeles.backend.repository.ProveedorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ProveedorServiceTest {

    private ProveedorRepository proveedorRepository;
    private ProveedorService proveedorService;

    @BeforeEach
    void setUp() {
        // Se crea el mock del repositorio y se instancia el servicio
        proveedorRepository = mock(ProveedorRepository.class);
        proveedorService = new ProveedorService(proveedorRepository);
    }

    @Test
    void testObtenerTodos() {
        // Prueba obtener todos los proveedores
        when(proveedorRepository.findAll()).thenReturn(List.of(new Proveedor()));

        List<Proveedor> result = proveedorService.obtenerTodos();

        assertEquals(1, result.size());
    }

    @Test
    void testObtenerPorId() {
        // Prueba obtener proveedor por ID
        Proveedor p = new Proveedor(1L, "Editorial", "contacto@mail.com");
        when(proveedorRepository.findById(1L)).thenReturn(Optional.of(p));

        Optional<Proveedor> result = proveedorService.obtenerPorId(1L);

        assertTrue(result.isPresent());
        assertEquals("Editorial", result.get().getNombre());
    }

    @Test
    void testGuardar() {
        // Prueba guardar un proveedor
        Proveedor p = new Proveedor();
        when(proveedorRepository.save(p)).thenReturn(p);

        Proveedor result = proveedorService.guardar(p);

        assertNotNull(result);
        verify(proveedorRepository).save(p);
    }

    @Test
    void testEliminar() {
        // Prueba eliminar proveedor por ID
        proveedorService.eliminar(7L);
        verify(proveedorRepository).deleteById(7L);
    }
}

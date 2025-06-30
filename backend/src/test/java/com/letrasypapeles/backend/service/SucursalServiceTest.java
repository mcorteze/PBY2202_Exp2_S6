package com.letrasypapeles.backend.service;

import com.letrasypapeles.backend.entity.Sucursal;
import com.letrasypapeles.backend.repository.SucursalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SucursalServiceTest {

    private SucursalRepository sucursalRepository;
    private SucursalService sucursalService;

    @BeforeEach
    void setUp() {
        // Se crea el mock del repositorio y se instancia el servicio
        sucursalRepository = mock(SucursalRepository.class);
        sucursalService = new SucursalService(sucursalRepository);
    }

    @Test
    void testObtenerTodas() {
        // Prueba obtener todas las sucursales
        when(sucursalRepository.findAll()).thenReturn(List.of(new Sucursal()));

        List<Sucursal> result = sucursalService.obtenerTodas();

        assertEquals(1, result.size());
    }

    @Test
    void testObtenerPorId() {
        // Prueba obtener sucursal por ID
        Sucursal s = new Sucursal(1L, "Sucursal Norte", "Calle Falsa", "Región X");
        when(sucursalRepository.findById(1L)).thenReturn(Optional.of(s));

        Optional<Sucursal> result = sucursalService.obtenerPorId(1L);

        assertTrue(result.isPresent());
        assertEquals("Sucursal Norte", result.get().getNombre());
    }

    @Test
    void testGuardar() {
        // Prueba guardar una sucursal
        Sucursal s = new Sucursal();
        when(sucursalRepository.save(s)).thenReturn(s);

        Sucursal result = sucursalService.guardar(s);

        assertNotNull(result);
        verify(sucursalRepository).save(s);
    }

    @Test
    void testEliminar() {
        // Prueba eliminar sucursal por ID
        sucursalService.eliminar(18L);
        verify(sucursalRepository).deleteById(18L);
    }
}

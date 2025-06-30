package com.letrasypapeles.backend.service;

import com.letrasypapeles.backend.entity.Categoria;
import com.letrasypapeles.backend.repository.CategoriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CategoriaServiceTest {

    private CategoriaRepository categoriaRepository;
    private CategoriaService categoriaService;

    @BeforeEach
    void setUp() {
        // Se crea el mock del repositorio y se instancia el servicio
        categoriaRepository = mock(CategoriaRepository.class);
        categoriaService = new CategoriaService(categoriaRepository);
    }

    @Test
    void testObtenerTodas() {
        // Prueba obtener todas las categorías
        Categoria cat1 = new Categoria(1L, "Ficción");
        Categoria cat2 = new Categoria(2L, "No Ficción");
        when(categoriaRepository.findAll()).thenReturn(Arrays.asList(cat1, cat2));

        List<Categoria> result = categoriaService.obtenerTodas();

        assertEquals(2, result.size());
        verify(categoriaRepository, times(1)).findAll();
    }

    @Test
    void testObtenerPorId() {
        // Prueba obtener categoría por ID existente
        Categoria cat = new Categoria(1L, "Ciencia");
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(cat));

        Optional<Categoria> result = categoriaService.obtenerPorId(1L);

        assertTrue(result.isPresent());
        assertEquals("Ciencia", result.get().getNombre());
    }

    @Test
    void testGuardar() {
        // Prueba guardar nueva categoría
        Categoria cat = new Categoria(null, "Arte");
        Categoria saved = new Categoria(10L, "Arte");

        when(categoriaRepository.save(cat)).thenReturn(saved);

        Categoria result = categoriaService.guardar(cat);

        assertEquals(saved.getId(), result.getId());
        verify(categoriaRepository, times(1)).save(cat);
    }

    @Test
    void testEliminar() {
        // Prueba eliminar categoría por ID
        categoriaService.eliminar(5L);
        verify(categoriaRepository, times(1)).deleteById(5L);
    }
}

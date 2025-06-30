package com.letrasypapeles.backend.controller;

import com.letrasypapeles.backend.entity.Categoria;
import com.letrasypapeles.backend.service.CategoriaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CategoriaControllerTest {

    private CategoriaService categoriaService;
    private CategoriaController categoriaController;

    @BeforeEach
    void setUp() {
        // Se crea el mock del servicio y el controlador
        categoriaService = mock(CategoriaService.class);
        categoriaController = new CategoriaController(categoriaService);
    }

    @Test
    void testObtenerTodas() {
        // Simula que se retorna una lista con al menos una categoría
        when(categoriaService.obtenerTodas()).thenReturn(List.of(new Categoria()));

        ResponseEntity<List<Categoria>> response = categoriaController.obtenerTodas();

        // Verifica que la respuesta tenga código 200 y lista no vacía
        assertEquals(200, response.getStatusCodeValue());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    void testObtenerPorIdFound() {
        // Simula búsqueda exitosa por ID
        Categoria cat = new Categoria(1L, "Libros");
        when(categoriaService.obtenerPorId(1L)).thenReturn(Optional.of(cat));

        ResponseEntity<Categoria> response = categoriaController.obtenerPorId(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Libros", response.getBody().getNombre());
    }

    @Test
    void testObtenerPorIdNotFound() {
        // Simula búsqueda por ID que no encuentra datos
        when(categoriaService.obtenerPorId(99L)).thenReturn(Optional.empty());

        ResponseEntity<Categoria> response = categoriaController.obtenerPorId(99L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCrearCategoria() {
        // Simula creación exitosa de categoría
        Categoria cat = new Categoria(1L, "Nueva");
        when(categoriaService.guardar(cat)).thenReturn(cat);

        ResponseEntity<Categoria> response = categoriaController.crearCategoria(cat);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Nueva", response.getBody().getNombre());
    }

    @Test
    void testActualizarCategoria() {
        // Simula actualización exitosa de categoría existente
        Categoria existing = new Categoria(1L, "Old");
        Categoria updated = new Categoria(1L, "Updated");

        when(categoriaService.obtenerPorId(1L)).thenReturn(Optional.of(existing));
        when(categoriaService.guardar(updated)).thenReturn(updated);

        ResponseEntity<Categoria> response = categoriaController.actualizarCategoria(1L, updated);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Updated", response.getBody().getNombre());
    }

    @Test
    void testActualizarCategoriaNotFound() {
        // Simula intento de actualizar categoría inexistente
        when(categoriaService.obtenerPorId(99L)).thenReturn(Optional.empty());

        ResponseEntity<Categoria> response = categoriaController.actualizarCategoria(99L, new Categoria());

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testEliminarCategoria() {
        // Simula eliminación exitosa de categoría
        Categoria cat = new Categoria(1L, "Borrar");
        when(categoriaService.obtenerPorId(1L)).thenReturn(Optional.of(cat));

        ResponseEntity<Void> response = categoriaController.eliminarCategoria(1L);

        assertEquals(200, response.getStatusCodeValue());
        verify(categoriaService).eliminar(1L);
    }

    @Test
    void testEliminarCategoriaNotFound() {
        // Simula intento de eliminar categoría inexistente
        when(categoriaService.obtenerPorId(99L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = categoriaController.eliminarCategoria(99L);

        assertEquals(404, response.getStatusCodeValue());
    }
}

package com.letrasypapeles.backend;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class BackendApplicationTest {

    @Test
    void testMain() {
        // Prueba que la aplicación arranca sin lanzar excepciones
        assertDoesNotThrow(() -> BackendApplication.main(new String[]{}));
    }
}

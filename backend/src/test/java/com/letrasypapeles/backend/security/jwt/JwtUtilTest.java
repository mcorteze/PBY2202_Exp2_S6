package com.letrasypapeles.backend.security.jwt;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    @Test
    void testGenerateAndValidateToken() {
        // Prueba generación y validación de token válido
        JwtUtil jwtUtil = new JwtUtil("12345678901234567890123456789012", 60000);
        UserDetails userDetails = new User("testuser", "pass", List.of());
        String token = jwtUtil.generateToken(userDetails);

        assertNotNull(token);

        String username = jwtUtil.getUsernameFromToken(token);
        assertEquals("testuser", username);

        assertTrue(jwtUtil.validateToken(token));
    }

    @Test
    void testValidateToken_invalidToken() {
        // Prueba validación de token inválido
        JwtUtil jwtUtil = new JwtUtil("12345678901234567890123456789012", 60000);
        String fakeToken = "fake.token.here";
        assertFalse(jwtUtil.validateToken(fakeToken));
    }

    @Test
    void testShortSecretThrowsException() {
        // Prueba que se lance excepción si la clave secreta es demasiado corta
        JwtUtil jwtUtil = new JwtUtil("shortkey", 60000);
        UserDetails userDetails = new User("testuser", "pass", List.of());
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            jwtUtil.generateToken(userDetails);
        });

        assertTrue(ex.getMessage().contains("La clave secreta debe tener al menos 256 bits"));
    }
}

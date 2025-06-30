package com.letrasypapeles.backend.controller;

import com.letrasypapeles.backend.security.jwt.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    private AuthController authController;

    @BeforeEach
    void setUp() {
        // Se crean los mocks necesarios para el controlador
        authenticationManager = mock(AuthenticationManager.class);
        jwtUtil = mock(JwtUtil.class);
        authController = new AuthController(authenticationManager, jwtUtil);
    }

    @Test
    void testLoginSuccess() {
        // Datos de prueba para login exitoso
        AuthController.LoginRequest req = new AuthController.LoginRequest();
        req.setEmail("user@test.com");
        req.setContraseña("1234");

        Authentication authentication = mock(Authentication.class);
        UserDetails userDetails = new User("user@test.com", "1234", List.of());

        // Configura el mock para simular autenticación exitosa
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtUtil.generateToken(userDetails)).thenReturn("fakeToken");

        ResponseEntity<Map<String, String>> response = authController.login(req);

        // Verifica respuesta con token generado
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("fakeToken", response.getBody().get("token"));
    }

    @Test
    void testLoginBadCredentials() {
        // Datos de prueba para credenciales incorrectas
        AuthController.LoginRequest req = new AuthController.LoginRequest();
        req.setEmail("wrong@test.com");
        req.setContraseña("wrongpass");

        // Simula error de credenciales
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        ResponseEntity<Map<String, String>> response = authController.login(req);

        // Verifica que se devuelva 401 y cuerpo vacío
        assertEquals(401, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testLoginInternalServerError() {
        // Datos de prueba para error interno
        AuthController.LoginRequest req = new AuthController.LoginRequest();
        req.setEmail("error@test.com");
        req.setContraseña("errorpass");

        // Simula excepción inesperada
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Unexpected error"));

        ResponseEntity<Map<String, String>> response = authController.login(req);

        // Verifica que se devuelva 500 por error interno
        assertEquals(500, response.getStatusCodeValue());
    }
}

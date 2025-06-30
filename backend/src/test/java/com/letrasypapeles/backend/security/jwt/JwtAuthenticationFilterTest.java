package com.letrasypapeles.backend.security.jwt;

import com.letrasypapeles.backend.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.List;

class JwtAuthenticationFilterTest {

    private JwtUtil jwtUtil;
    private UsuarioService usuarioService;
    private JwtAuthenticationFilter filter;

    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        // Se crean los mocks necesarios para el filtro
        jwtUtil = mock(JwtUtil.class);
        usuarioService = mock(UsuarioService.class);
        filter = new JwtAuthenticationFilter(jwtUtil, usuarioService);

        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);

        // Reinicia el SecurityContext antes de cada prueba
        SecurityContextHolder.clearContext();
    }

    @Test
    void testNoHeader() throws ServletException, IOException {
        // Caso: no se envía cabecera Authorization
        when(request.getHeader("Authorization")).thenReturn(null);

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testHeaderWithoutBearer() throws ServletException, IOException {
        // Caso: la cabecera no empieza con Bearer
        when(request.getHeader("Authorization")).thenReturn("Token abcdef");

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testInvalidTokenException() throws ServletException, IOException {
        // Caso: excepción al intentar obtener el usuario desde el token
        when(request.getHeader("Authorization")).thenReturn("Bearer invalidtoken");
        when(jwtUtil.getUsernameFromToken("invalidtoken")).thenThrow(new RuntimeException("Invalid token"));

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testUsernameNull() throws ServletException, IOException {
        // Caso: token válido pero no retorna username
        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(jwtUtil.getUsernameFromToken("token")).thenReturn(null);

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testTokenInvalidValidation() throws ServletException, IOException {
        // Caso: token con username pero inválido al validarse
        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(jwtUtil.getUsernameFromToken("token")).thenReturn("user");
        when(jwtUtil.validateToken("token")).thenReturn(false);

        UserDetails userDetails = new User("user", "pass", List.of());
        when(usuarioService.loadUserByUsername("user")).thenReturn(userDetails);

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testTokenValidAuthentication() throws ServletException, IOException {
        // Caso: token válido que genera autenticación
        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(jwtUtil.getUsernameFromToken("token")).thenReturn("user");
        when(jwtUtil.validateToken("token")).thenReturn(true);

        UserDetails userDetails = new User("user", "pass", List.of());
        when(usuarioService.loadUserByUsername("user")).thenReturn(userDetails);

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);

        // Verifica que se haya establecido autenticación en el contexto
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertTrue(SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken);
    }

    @Test
    void testAlreadyAuthenticated() throws ServletException, IOException {
        // Caso: ya existe autenticación en el contexto
        SecurityContextHolder.getContext().setAuthentication(mock(UsernamePasswordAuthenticationToken.class));

        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(jwtUtil.getUsernameFromToken("token")).thenReturn("user");

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }

    @Test
    void testErrorDuringAuthentication() throws ServletException, IOException {
        // Caso: error inesperado al cargar usuario
        when(request.getHeader("Authorization")).thenReturn("Bearer token");
        when(jwtUtil.getUsernameFromToken("token")).thenReturn("user");
        when(jwtUtil.validateToken("token")).thenReturn(true);

        when(usuarioService.loadUserByUsername("user"))
                .thenThrow(new RuntimeException("Unexpected error"));

        filter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }
}

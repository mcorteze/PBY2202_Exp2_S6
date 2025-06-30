package com.letrasypapeles.backend.security;

import com.letrasypapeles.backend.entity.Cliente;
import com.letrasypapeles.backend.security.jwt.JwtUtil;
import com.letrasypapeles.backend.service.ClienteService;
import com.letrasypapeles.backend.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtil jwtUtil;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        // Configura valores privados de JwtUtil mediante Reflection
        ReflectionTestUtils.setField(jwtUtil, "jwtSecret", "12345678901234567890123456789012");
        ReflectionTestUtils.setField(jwtUtil, "jwtExpirationMs", 60000);

        // Define un usuario con rol ADMIN simulado
        UserDetails userDetails = new User(
                "admin",
                "pass",
                List.of(() -> "ROLE_ADMIN")
        );

        when(usuarioService.loadUserByUsername("admin"))
                .thenReturn(userDetails);

        when(clienteService.obtenerTodos())
                .thenReturn(List.of(new Cliente()));
    }

    @Test
    void testAdminEndpointWithToken() throws Exception {
        // Prueba acceso autorizado con token válido
        UserDetails userDetails = new User(
                "admin",
                "pass",
                List.of(() -> "ROLE_ADMIN")
        );

        String token = jwtUtil.generateToken(userDetails);

        mockMvc.perform(get("/api/clientes")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testUnauthorizedAccessWithoutToken() throws Exception {
        // Prueba acceso denegado sin token
        mockMvc.perform(get("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}

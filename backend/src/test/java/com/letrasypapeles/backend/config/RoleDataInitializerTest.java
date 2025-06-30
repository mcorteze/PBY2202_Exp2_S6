package com.letrasypapeles.backend.config;

import com.letrasypapeles.backend.entity.Role;
import com.letrasypapeles.backend.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

class RoleDataInitializerTest {

    private RoleRepository roleRepository;
    private RoleDataInitializer initializer;

    @BeforeEach
    void setUp() {
        // Se crea un mock del repositorio de roles
        roleRepository = mock(RoleRepository.class);
        initializer = new RoleDataInitializer(roleRepository);
    }

    @Test
    void testRun_createsRolesIfNotExist() {
        // Simula que no existen roles en la base de datos
        when(roleRepository.findByNombre(anyString())).thenReturn(Optional.empty());
        when(roleRepository.save(any(Role.class))).thenAnswer(i -> i.getArgument(0));

        initializer.run();

        // Verifica que se intente buscar y guardar los 4 roles
        verify(roleRepository, times(4)).findByNombre(anyString());
        verify(roleRepository, times(4)).save(any(Role.class));
    }

    @Test
    void testRun_doesNotCreateIfExists() {
        // Simula que ya existe un rol en la base de datos
        when(roleRepository.findByNombre(anyString()))
                .thenReturn(Optional.of(new Role("ADMIN")));

        initializer.run();

        // Verifica que se consulte 4 veces pero no se guarden roles nuevos
        verify(roleRepository, times(4)).findByNombre(anyString());
        verify(roleRepository, never()).save(any(Role.class));
    }
}

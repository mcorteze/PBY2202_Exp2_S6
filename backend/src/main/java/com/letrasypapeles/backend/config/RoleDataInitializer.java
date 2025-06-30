package com.letrasypapeles.backend.config;

import com.letrasypapeles.backend.entity.Role;
import com.letrasypapeles.backend.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleDataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        createRoleIfNotExists("CLIENTE");
        createRoleIfNotExists("EMPLEADO");
        createRoleIfNotExists("GERENTE");
        createRoleIfNotExists("ADMIN");
    }

    private void createRoleIfNotExists(String nombre) {
        roleRepository.findByNombre(nombre)
                .orElseGet(() -> roleRepository.save(new Role(nombre)));
    }
}

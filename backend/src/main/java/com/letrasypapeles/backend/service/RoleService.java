package com.letrasypapeles.backend.service;

import com.letrasypapeles.backend.entity.Role;
import com.letrasypapeles.backend.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<Role> obtenerTodos() {
        return roleRepository.findAll();
    }

    public Optional<Role> obtenerPorNombre(String nombre) {
        return roleRepository.findByNombre(nombre);
    }

    public Role guardar(Role role) {
        return roleRepository.save(role);
    }

    public void eliminar(String nombre) {
        roleRepository.deleteById(nombre);
    }
}

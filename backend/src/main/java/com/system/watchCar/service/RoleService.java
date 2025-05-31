package com.system.watchCar.service;

import com.system.watchCar.dto.RoleDTO;
import com.system.watchCar.entity.Role;
import com.system.watchCar.entity.RoleType;
import com.system.watchCar.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    @Transactional(readOnly = true)
    public RoleDTO findById(Long id) {
        Role role = repository.findById(id).orElseThrow(()->new RuntimeException("Role not found"));
        return new RoleDTO(role.getIdRole(), role.getAuthority());
    }

    // Method to find a role by its authority
    @Transactional(readOnly = true)
    public RoleDTO findByAuthority(String authority) {
        Role role = repository.findByAuthority(authority);
        if (Objects.isNull(role)) {
            throw new RuntimeException("Role not found");
        }
        return new RoleDTO(role.getIdRole(), role.getAuthority());
    }

    public RoleDTO findByAuthority(RoleType roleType) {
        return findByAuthority(roleType.name());
    }

    @Transactional(readOnly = true)
    public List<RoleDTO> findAll() {
        return repository.findAll().stream().map(r-> new RoleDTO(r.getIdRole(), r.getAuthority())).toList();
    }
}

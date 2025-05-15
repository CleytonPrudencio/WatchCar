package com.system.watchCar.service;

import com.system.watchCar.entity.Role;
import com.system.watchCar.entity.RoleType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RoleService {

    private List<RoleType> roleTypeList = List.of(RoleType.values());

    public RoleType findById(long id) {
        if(id < 1 || id > roleTypeList.size()) {
            throw new IllegalArgumentException("ID inválido");
        }
        return roleTypeList.get((int)(id - 1));
    }

    public Role findByIdReturnRole(long id) {
        System.out.println("size list: "+ roleTypeList.size() +" - ID: " + id+" - class: "+ getClass());
        if(id < 1 || id > roleTypeList.size()) {
            throw new IllegalArgumentException("ID inválido");
        }
        Role role = new Role();
        role.setId(id);
        role.setName(roleTypeList.get((int)(id - 1)));
        return role;
    }

    public RoleType findByName(String name) {
        if(Objects.isNull(name) || name.isBlank()) {
            throw new IllegalArgumentException("Nome inválido");
        }
        for (RoleType roleType : roleTypeList) {
            if (roleType.name().equalsIgnoreCase(name.toUpperCase())) {
                return roleType;
            }
        }
        throw new IllegalArgumentException("Nome inválido");
    }
}

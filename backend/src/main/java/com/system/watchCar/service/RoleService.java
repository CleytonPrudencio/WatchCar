package com.system.watchCar.service;

import com.system.watchCar.entity.Role;
import com.system.watchCar.entity.RoleType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class RoleService {

    private List<RoleType> roleTypeList = List.of(RoleType.values());

    public Role findById(long id) {
        if (id < 1 || id > roleTypeList.size()) {
            throw new IllegalArgumentException("ID inválido");
        }
        return new Role(id, roleTypeList.get((int) (id - 1)));
    }

    public Role findByName(String name) {
        if (Objects.isNull(name) || name.isBlank()) {
            throw new IllegalArgumentException("Nome inválido");
        }
        for (RoleType roleType : roleTypeList) {
            if (roleType.name().equalsIgnoreCase(name)) {
                return new Role(Long.valueOf(roleType.ordinal() + 1), roleType);
            }
        }
        throw new IllegalArgumentException("Nome inválido");
    }
}

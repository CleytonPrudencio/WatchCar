package com.system.watchCar.dto.response;

import com.system.watchCar.dto.RoleDTO;
import com.system.watchCar.interfaces.IGestorSecurity;
import com.system.watchCar.interfaces.IResponseOK;
import com.system.watchCar.interfaces.IRole;

public interface UserProjection extends IGestorSecurity, IRole, IResponseOK {

    default IGestorSecurity toGestor() {
        IGestorSecurity user = new UserResponse();
        user.setIdUser(getIdUser());
        user.setUserName(getUserName());
        user.setPassword(getPassword());
        user.setEmail(getEmail());
        user.setCpf(getCpf());
        user.setUserActivated(getUserActivated());

        RoleDTO role = new RoleDTO();
        role.setIdRole(getIdRole());
        role.setAuthority(getAuthority());
        user.addRole(role);

        // Agente de segurança
        user.setDelegate(getDelegate());
        user.setBadge(getBadge());
        user.setRa(getRa());

        // Gestor de segurança
        user.setDepartment(getDepartment());
        user.setCargo(getCargo());

        return user;
    }

}

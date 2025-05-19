package com.system.watchCar.interfaces;

import com.system.watchCar.entity.Role;
import com.system.watchCar.service.exceptions.UserExecption;

public interface IUser extends IGestorSecurity, IAgente {

    void setRole(Role role);
    Role getRole();

    default <U extends IUser> U toUser(Class<U> clazz) {
        try {
            U user = toUserSimple(clazz);
            user.setRole(getRole());

            // Gestor
            user.setDepartment(getDepartment());
            user.setCargo(getCargo());

            // Agente
            user.setDelegate(getDelegate());
            user.setBadge(getBadge());
            user.setRa(getRa());
            user.setDepartment(getDepartment());

            return user;
        } catch (Exception e) {
            throw new UserExecption("Error converting to class from " + clazz.getSimpleName(), e);
        }
    }
}

package com.system.watchCar.interfaces;

import com.system.watchCar.service.exceptions.UserExecption;

public interface IGestorSecurity extends IUserSimple{

    void setDepartment(String department);
    String getDepartment();

    void setCargo(String cargo);
    String getCargo();

    default <U extends IGestorSecurity> U toGestor(Class<U> clazz) {
        try {
            U gestor = toUserSimple(clazz);
            gestor.setDepartment(getDepartment());
            gestor.setCargo(getCargo());
            return gestor;
        }catch (Exception e){
            throw new UserExecption("Error converting to class from "+ clazz.getSimpleName(), e);
        }
    }
}

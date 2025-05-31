package com.system.watchCar.interfaces;

import com.system.watchCar.service.exceptions.UserExecption;

public interface IUser extends IUserSimple {

    IUser setIdCarro(Long idCaro);
    Long getIdCarro();

    IUser setIdLocal(Long idLocal);
    Long getIdLocal();

    default <U extends IUser> U toUser(Class<U> clazz) {
        try {
            U user = toUserSimple(clazz);
            user.setIdCarro(getIdCarro());
            user.setIdLocal(getIdLocal());
            return user;
        } catch (Exception e) {
            throw new UserExecption("Error converting to class from " + clazz.getSimpleName(), e);
        }
    }
}

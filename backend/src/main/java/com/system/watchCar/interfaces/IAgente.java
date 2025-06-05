package com.system.watchCar.interfaces;

import com.system.watchCar.service.exceptions.UserExecption;

public interface IAgente extends IUserSimple{
    IAgente setDelegate(String delegate);
    String getDelegate();

    IAgente setBadge(String badge);
    String getBadge();

    IAgente setRa(String ra);
    String getRa();

    default <U extends IAgente> U toAgente(Class<U> clazz) {
        try {
            U agente = toUserSimple(clazz);
            agente.setDelegate(getDelegate());
            agente.setBadge(getBadge());
            agente.setRa(getRa());
            return agente;
        }catch (Exception e){
            throw new UserExecption("Error converting to class from "+ clazz.getSimpleName(), e);
        }
    }
}

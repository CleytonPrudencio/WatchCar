package com.system.watchCar.interfaces;

import com.system.watchCar.service.exceptions.UserExecption;

public interface IAgente extends IUserSimple{
    void setDelegate(String delegate);
    String getDelegate();

    void setBadge(String badge);
    String getBadge();

    void setRa(String ra);
    String getRa();

    void setDepartment(String department);
    String getDepartment();

    default <U extends IAgente> U toAgente(Class<U> clazz) {
        try {
            U agente = toUserSimple(clazz);
            agente.setDelegate(getDelegate());
            agente.setBadge(getBadge());
            agente.setRa(getRa());
            agente.setDepartment(getDepartment());
            return agente;
        }catch (Exception e){
            throw new UserExecption("Error converting to class from "+ clazz.getSimpleName(), e);
        }
    }
}

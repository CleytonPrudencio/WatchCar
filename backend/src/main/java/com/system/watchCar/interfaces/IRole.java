package com.system.watchCar.interfaces;

import org.springframework.security.core.GrantedAuthority;

public interface IRole extends GrantedAuthority {

    IRole setIdRole(Long id);
    Long getIdRole();

    IRole setAuthority(String authority);

    default <R extends IRole> R toRole(Class<R> clazz) {
        try {
            R instance = clazz.getDeclaredConstructor().newInstance();
            instance.setIdRole(getIdRole());
            instance.setAuthority(getAuthority());
            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Error creating role instance: "+ clazz.getSimpleName(), e);
        }
    }
}

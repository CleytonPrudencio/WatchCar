package com.system.watchCar.interfaces;

public interface IRole {

    IRole setRoleId(Long id);
    Long getRoleId();

    IRole setAuthority(String authority);
    String getAuthority();

    default <R extends IRole> R toRole(Class<R> clazz) {
        try {
            R instance = clazz.getDeclaredConstructor().newInstance();
            instance.setRoleId(getRoleId());
            instance.setAuthority(getAuthority());
            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Error creating role instance: "+ clazz.getSimpleName(), e);
        }
    }
}

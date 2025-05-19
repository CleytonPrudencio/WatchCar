package com.system.watchCar.interfaces;

import com.system.watchCar.service.exceptions.UserExecption;

public interface IUserSimple {

    void setIdUser(Long id);
    Long getIdUser();

    void setUserName(String username);
    String getUsername();

    void setPassword(String password);
    String getPassword();

    void setEmail(String email);
    String getEmail();

    void setActiveUser(boolean active);
    Boolean getActiveUser();

    default <U extends IUserSimple> U toUserSimple(Class<U> clazz) {
        try {
            U user = clazz.getDeclaredConstructor().newInstance();
            user.setIdUser(getIdUser());
            user.setUserName(getUsername());
            user.setPassword(getPassword());
            user.setEmail(getEmail());
            user.setActiveUser(getActiveUser());
            return user;
        }catch (Exception e){
            throw new UserExecption("Error converting to "+ clazz.getSimpleName());
        }
    }
}

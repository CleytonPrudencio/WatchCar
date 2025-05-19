package com.system.watchCar.interfaces;

public interface IUser {

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
}

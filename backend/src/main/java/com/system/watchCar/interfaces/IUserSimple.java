package com.system.watchCar.interfaces;

import com.system.watchCar.service.exceptions.UserExecption;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public interface IUserSimple {

    IUserSimple setIdUser(Long id);
    Long getIdUser();

    IUserSimple setUserName(String username);
    String getUserName();

    IUserSimple setPassword(String password);
    String getPassword();

    IUserSimple setCpf(String cpf);
    String getCpf();

    IUserSimple setEmail(String email);
    String getEmail();

    IUserSimple setUserActivated(boolean active);
    Boolean getUserActivated();

    IUserSimple addRole(IRole role);
    Collection<? extends IRole> getRoles();

    default <U extends IUserSimple> U toUserSimple(Class<U> clazz) {
        try {
            U user = clazz.getDeclaredConstructor().newInstance();
            user.setIdUser(getIdUser());
            user.setUserName(getUserName());
            user.setPassword(getPassword());
            user.setEmail(getEmail());
            user.setCpf(getCpf());
            user.setUserActivated(getUserActivated());

            // Adiciona as roles ao usuário
            for(IRole role : getRoles()) {
                user.addRole(role);
            }
            return user;
        }catch (Exception e){
            throw new UserExecption("Error converting to "+ clazz.getSimpleName());
        }
    }
}

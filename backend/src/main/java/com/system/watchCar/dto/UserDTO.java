package com.system.watchCar.dto;

import com.system.watchCar.entity.Role;
import com.system.watchCar.entity.User;
import com.system.watchCar.interfaces.IRole;
import com.system.watchCar.interfaces.IUserSimple;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements IUserSimple {

    private Long idUser;
    private String name;
    private String password;
    private String email;
    private String cpf;
    private Boolean ativo;
    private List<RoleDTO> roles = new ArrayList<>();


    public UserDTO(User entity) {
        this.idUser = entity.getIdUser();
        this.name = entity.getUserName();
        this.password = entity.getPassword();
        this.email = entity.getEmail();
        this.cpf = entity.getCpf();

        if (entity.getRoles() != null) {
            for (Role role : entity.getRoles()) {
                roles.add(new RoleDTO(role.getIdRole(), role.getAuthority()));
            }
        }
    }

    @Override
    public UserDTO setIdUser(Long id) {
        this.idUser = id;
        return this;
    }

    @Override
    public Long getIdUser() {
        return idUser;
    }

    @Override
    public UserDTO setUserName(String username) {
        this.name = username;
        return this;
    }

    @Override
    public String getUserName() {
        return name;
    }

    @Override
    public UserDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public IUserSimple setCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    @Override
    public String getCpf() {
        return cpf;
    }

    @Override
    public UserDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public UserDTO setUserActivated(boolean active) {
        this.ativo = active;
        return this;
    }

    @Override
    public Boolean getUserActivated() {
        return ativo;
    }

    @Override
    public UserDTO addRole(IRole role) {
        roles.add(role.toRole(RoleDTO.class));
        return this;
    }

    @Override
    public List<RoleDTO> getRoles() {
        return roles;
    }
}

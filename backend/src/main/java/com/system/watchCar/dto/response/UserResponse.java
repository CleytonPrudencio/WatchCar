package com.system.watchCar.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.system.watchCar.dto.RoleDTO;
import com.system.watchCar.entity.Role;
import com.system.watchCar.entity.User;
import com.system.watchCar.interfaces.IResponseOK;
import com.system.watchCar.interfaces.IRole;
import com.system.watchCar.interfaces.IUserSimple;

import java.util.ArrayList;
import java.util.List;

public class UserResponse implements IUserSimple, IResponseOK {

    private Long idUser;
    private String name;
    @JsonIgnore
    private String password;
    private String email;
    private String cpf;
    private boolean ativo;
    private List<RoleDTO> roles = new ArrayList<>();

    public UserResponse() {
    }

    public UserResponse(User entity) {
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
    public UserResponse setIdUser(Long id) {
        this.idUser = id;
        return this;
    }

    @JsonProperty("id")
    @Override
    public Long getIdUser() {
        return idUser;
    }

    @Override
    public UserResponse setUserName(String username) {
        this.name = username;
        return this;
    }

    @JsonProperty("name")
    @Override
    public String getUserName() {
        return name;
    }

    @Override
    public UserResponse setPassword(String password) {
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
    public UserResponse setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public UserResponse setUserActivated(boolean active) {
        this.ativo = active;
        return this;
    }

    @JsonProperty("ativo")
    @Override
    public boolean getUserActivated() {
        return ativo;
    }

    @Override
    public UserResponse addRole(IRole role) {
        roles.add(role.toRole(RoleDTO.class));
        return this;
    }

    @Override
    public List<RoleDTO> getRoles() {
        return roles;
    }

    @Override
    public boolean getSuccess() {
        return !email.isBlank();
    }
}

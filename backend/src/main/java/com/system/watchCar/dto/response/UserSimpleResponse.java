package com.system.watchCar.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.system.watchCar.dto.RoleDTO;
import com.system.watchCar.interfaces.IResponseOK;
import com.system.watchCar.interfaces.IRole;
import com.system.watchCar.interfaces.IUserSimple;

import java.util.ArrayList;
import java.util.List;

public class UserSimpleResponse implements IUserSimple, IResponseOK {

    private Long idUser;
    private String name;
    private String email;
    private List<RoleDTO> roles = new ArrayList<>();

    public UserSimpleResponse() {
    }

    @Override
    public UserSimpleResponse setIdUser(Long id) {
        this.idUser = id;
        return this;
    }

    @JsonProperty("id")
    @Override
    public Long getIdUser() {
        return idUser;
    }

    @Override
    public UserSimpleResponse setUserName(String username) {
        this.name = username;
        return this;
    }

    @JsonProperty("name")
    @Override
    public String getUserName() {
        return name;
    }

    @JsonIgnore
    @Override
    public UserSimpleResponse setPassword(String password) {
        return this;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public IUserSimple setCpf(String cpf) {
        return this;
    }

    @Override
    public String getCpf() {
        return null;
    }

    @Override
    public UserSimpleResponse setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public UserSimpleResponse setUserActivated(boolean active) {
        return this;
    }

    @JsonProperty("ativo")
    @Override
    public Boolean getUserActivated() {
        return null;
    }

    @Override
    public UserSimpleResponse addRole(IRole role) {
        roles.add(role.toRole(RoleDTO.class));
        return this;
    }

    @JsonProperty("tipo")
    @Override
    public List<RoleDTO> getRoles() {
        return roles;
    }

    @Override
    public boolean getSuccess() {
        return (!email.isBlank());
    }
}

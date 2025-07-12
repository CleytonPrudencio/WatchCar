package com.system.watchCar.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.system.watchCar.dto.RoleDTO;
import com.system.watchCar.interfaces.IGestorSecurity;
import com.system.watchCar.interfaces.IResponseOK;
import com.system.watchCar.interfaces.IRole;
import com.system.watchCar.interfaces.IUserSimple;

import java.util.ArrayList;
import java.util.List;

public class UserResponse implements IGestorSecurity, IResponseOK {

    private Long idUser;
    private String userName;
    @JsonIgnore
    private String password;
    private String email;
    private String cpf;
    private boolean ativo;

    private List<RoleDTO> roles = new ArrayList<>();

    // Dados do Agente
    private String delegate;
    private String badge;
    private String ra;

    // Dados do Gestor
    private String department;
    private String cargo;

    public UserResponse() {
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
        this.userName = username;
        return this;
    }

    @JsonProperty("name")
    @Override
    public String getUserName() {
        return userName;
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

    @Override
    public UserResponse setDelegate(String delegate) {
        this.delegate = delegate;
        return this;
    }

    @JsonProperty("delegacia")
    @Override
    public String getDelegate() {
        return delegate;
    }

    @Override
    public UserResponse setBadge(String badge) {
        this.badge = badge;
        return this;
    }

    @JsonProperty("distintivo")
    @Override
    public String getBadge() {
        return badge;
    }

    @Override
    public UserResponse setRa(String ra) {
        this.ra = ra;
        return this;
    }

    @Override
    public String getRa() {
        return ra;
    }

    @Override
    public UserResponse setDepartment(String department) {
        this.department = department;
        return this;
    }

    @JsonProperty("departamento")
    @Override
    public String getDepartment() {
        return department;
    }

    @Override
    public String getCargo() {
        return cargo;
    }

    @Override
    public UserResponse setCargo(String cargo) {
        this.cargo = cargo;
        return this;
    }
}

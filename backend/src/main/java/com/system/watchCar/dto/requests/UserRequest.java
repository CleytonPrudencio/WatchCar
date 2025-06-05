package com.system.watchCar.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.system.watchCar.dto.RoleDTO;
import com.system.watchCar.interfaces.IRole;
import com.system.watchCar.interfaces.IUserSimple;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

import java.util.ArrayList;
import java.util.List;


public class UserRequest implements IUserSimple {

    private Long idUser;
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
    @Email
    private String email;
    @CPF
    private String cpf;
    private List<RoleDTO> roles = new ArrayList<>();
    private Boolean activated = true;

    @Override
    public UserRequest setIdUser(Long id) {
        this.idUser = id;
        return this;
    }

    @JsonProperty("id")
    @Override
    public Long getIdUser() {
        return idUser;
    }

    @Override
    public UserRequest setUserName(String username) {
        this.userName = username;
        return this;
    }

    @JsonProperty("name")
    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public UserRequest setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public UserRequest setCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    @Override
    public String getCpf() {
        return cpf;
    }

    @Override
    public UserRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public UserRequest setUserActivated(boolean active) {
        this.activated = active;
        return this;
    }

    @JsonProperty("ativo")
    @Override
    public Boolean getUserActivated() {
        return activated;
    }

    @Override
    public UserRequest addRole(IRole role) {
        roles.add(role.toRole(RoleDTO.class));
        return this;
    }

    @Override
    public List<RoleDTO> getRoles() {
        return roles;
    }
}

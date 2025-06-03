package com.system.watchCar.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.system.watchCar.dto.RoleDTO;
import com.system.watchCar.interfaces.IAgente;
import com.system.watchCar.interfaces.IGestorSecurity;
import com.system.watchCar.interfaces.IRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

import java.util.ArrayList;
import java.util.List;


public class UserGestorRequest implements IGestorSecurity {

    @Schema(name = "id", description = "ID do usuário", example = "1")
    private Long idUser;

    @Schema(description = "Digite o nome do usuário", required = true, example = "João da Silva")
    @NotBlank(message = "Campo nome do usuário obrigatório")
    @JsonProperty("name")
    private String userName;

    @Schema(description = "Digite a senha do usuário", required = true, example = "123")
    @NotBlank
    private String password;

    @Schema(description = "Digite o email do usuário", required = true, example = "meu@mail.com")
    @Email(message = "E-mail inválido")
    private String email;

    @Schema(description = "Digite o CPF do usuário", required = true, example = "12345678901")
    @CPF(message = "CPF inválido")
    private String cpf;

    @Schema(description = "Lista de papéis do usuário", example = "[{\"idRole\": 1}]")
    private List<RoleDTO> roles = new ArrayList<>();

    @Schema(description = "Nome da delegacia", example = "73º DP")
    @JsonProperty("delegacia")
    private String delegate;

    @Schema(description = "Número do crachá", example = "123456")
    @JsonProperty("distintivo")
    private String badge;

    @Schema(description = "Número do RA", example = "1234")
    private String ra;

    @Schema(description = "Departamento do usuário", example = "Polícia Civil")
    @JsonProperty("departamento")
    private String department;

    @Schema(description = "Cargo do usuário", example = "Delegado")
    private String cargo;

    @Schema(description = "Indica se o usuário está ativo", example = "true")
    @JsonProperty("ativo")
    private Boolean activated = true;

    @Override
    public UserGestorRequest setIdUser(Long id) {
        this.idUser = id;
        return this;
    }

    @Override
    public Long getIdUser() {
        return idUser;
    }

    @Override
    public UserGestorRequest setUserName(String username) {
        this.userName = username;
        return this;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public UserGestorRequest setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public UserGestorRequest setCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    @Override
    public String getCpf() {
        return cpf;
    }

    @Override
    public UserGestorRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public UserGestorRequest setUserActivated(boolean active) {
        this.activated = active;
        return this;
    }

    @Override
    public Boolean getUserActivated() {
        return activated;
    }

    @Override
    public UserGestorRequest addRole(IRole role) {
        roles.add(role.toRole(RoleDTO.class));
        return this;
    }

    @Override
    public List<RoleDTO> getRoles() {
        return roles;
    }

    @Override
    public IAgente setDelegate(String delegate) {
        this.delegate = delegate;
        return this;
    }

    @Override
    public String getDelegate() {
        return delegate;
    }

    @Override
    public IAgente setBadge(String badge) {
        this.badge = badge;
        return this;
    }

    @Override
    public String getBadge() {
        return badge;
    }

    @Override
    public IAgente setRa(String ra) {
        this.ra = ra;
        return this;
    }

    @Override
    public String getRa() {
        return ra;
    }

    @Override
    public UserGestorRequest setDepartment(String department) {
        this.department = department;
        return this;
    }

    @Override
    public String getDepartment() {
        return department;
    }

    @Override
    public UserGestorRequest setCargo(String cargo) {
        this.cargo = cargo;
        return this;
    }

    @Override
    public String getCargo() {
        return cargo;
    }
}

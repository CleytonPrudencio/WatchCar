package com.system.watchCar.entity;

import com.system.watchCar.interfaces.IUser;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "TB_USERR")
public class Userr implements IUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotBlank
    @Column(name = "NOME")
    private String username;

    @NotBlank
    @Column(name = "SENHA")
    private String password;

    @Email
    @Column(name = "EMAIL")
    private String email;

    // Adicionando os campos CPF e ALERTA
    @Column(name = "CPF", length = 11)
    private String cpf;

    @Column(name = "ALERTA")
    private Boolean alerta; // ALERTA pode ser true ou false

    // Relacionamento com a role (papel do usuário)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TIPO", referencedColumnName = "ID")
    private Role role;

    // Campos adicionais para perfis específicos

    @Column(name = "DELEGACIA")
    private String delegate; // Para Policial, Agente de Segurança, Investigador

    @Column(name = "DISTINTIVO")
    private String badge; // Para Policial, Agente de Segurança, Investigador

    @Column(name = "RA")
    private String ra; // Para Policial, Agente de Segurança, Investigador

    @Column(name = "DEPARTAMENTO")
    private String departamento; // Para Gestor de Segurança Pública

    @Column(name = "CARGO")
    private String cargo; // Para Gestor de Segurança Pública

    @Column(name = "ATIVO")
    private Boolean ativo;

    @Override
    public void setIdUser(Long id) {
        this.id = id;
    }

    @Override
    public Long getIdUser() {
        return id;
    }

    @Override
    public void setUserName(String username) {
        this.username = username;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setActiveUser(boolean active) {
        this.alerta = active;
    }

    @Override
    public Boolean getActiveUser() {
        return alerta;
    }

    @Override
    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public Role getRole() {
        return role;
    }

    @Override
    public void setDelegate(String delegate) {
        this.delegate = delegate;
    }

    @Override
    public String getDelegate() {
        return delegate;
    }

    @Override
    public void setBadge(String badge) {
        this.badge = badge;
    }

    @Override
    public String getBadge() {
        return badge;
    }

    @Override
    public void setRa(String ra) {
        this.ra = ra;
    }

    @Override
    public String getRa() {
        return ra;
    }

    @Override
    public void setDepartment(String department) {
        this.departamento = department;
    }

    @Override
    public String getDepartment() {
        return departamento;
    }

    @Override
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    @Override
    public String getCargo() {
        return cargo;
    }
}

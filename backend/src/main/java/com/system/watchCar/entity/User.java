package com.system.watchCar.entity;

import com.system.watchCar.interfaces.IRole;
import com.system.watchCar.interfaces.IUserSimple;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TB_USER")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements IUserSimple, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @NotBlank
    @Column(nullable = false)
    private String userName;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @Email
    @Column(unique = true)
    private String email;

    // Adicionando os campos CPF e ALERTA
    @Column(unique = true, length = 11)
    private String cpf;

    private Boolean activated;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    @Override
    public User setIdUser(Long id) {
        this.idUser = id;
        return this;
    }

    @Override
    public Long getIdUser() {
        return idUser;
    }

    @Override
    public User setUserName(String username) {
        this.userName = username;
        return this;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public User setCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    @Override
    public String getCpf() {
        return cpf;
    }

    @Override
    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public Set<Role> getRoles() {
        return roles;
    }

    @Override
    public User setUserActivated(boolean active) {
        this.activated = active;
        return this;
    }

    @Override
    public Boolean getUserActivated() {
        return activated;
    }

    @Override
    public User addRole(IRole role) {
        roles.add(role.toRole(Role.class));
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    public boolean hasRole(String roleName) {
        for (Role role : roles) {
            if (role.getAuthority().equals(roleName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getUsername() {
        return cpf;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

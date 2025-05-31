package com.system.watchCar.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_USUARIO")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @Email
    @Column(unique = true)
    private String email;

    // Adicionando os campos CPF e ALERTA
    @Column(unique = true, length = 11)
    private String cpf;

    private Boolean alerta; // ALERTA pode ser true ou false

    // Campos adicionais para perfis específicos
    @Column(name = "DELEGACIA")
    private String delegate; // Para Policial, Agente de Segurança, Investigador

    @Column(name = "DISTINTIVO")
    private String badge; // Para Policial, Agente de Segurança, Investigador

    @Column(name = "RA")
    private String ra; // Para Policial, Agente de Segurança, Investigador

    private String departamento; // Para Gestor de Segurança Pública
    private String cargo; // Para Gestor de Segurança Pública
    private Boolean ativo;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "tb_user_role",
            joinColumns = @JoinColumn(name = "user_userId"),
            inverseJoinColumns = @JoinColumn(name = "role_roleId"))
    private Set<Role> roles = new HashSet<>();

    public User addRole(Role role) {
        this.roles.add(role);
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
        return email;
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

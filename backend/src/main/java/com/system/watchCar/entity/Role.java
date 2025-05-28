package com.system.watchCar.entity;

import com.system.watchCar.interfaces.IRole;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "TB_ROLE")
public class Role implements IRole, GrantedAuthority {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;
    private String authority;

    public Role() {
    }

    @Override
    public Role setRoleId(Long roleId) {
        this.roleId = roleId;
        return this;
    }

    @Override
    public Long getRoleId() {
        return roleId;
    }

    @Override
    public Role setAuthority(String authority) {
        this.authority = authority;
        return this;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}

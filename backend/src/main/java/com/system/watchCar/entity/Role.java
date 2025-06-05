package com.system.watchCar.entity;

import com.system.watchCar.interfaces.IRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Entity
@Table(name = "TB_ROLE")
public class Role implements IRole{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;
    private String authority;

    public Role() {
    }

    @Override
    public Role setIdRole(Long roleId) {
        this.roleId = roleId;
        return this;
    }

    @Override
    public Long getIdRole() {
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

package com.system.watchCar.dto;

import com.system.watchCar.interfaces.IRole;


public class RoleDTO implements IRole {

    private Long roleId;
    private String authority;

    public RoleDTO() {
    }

    public RoleDTO(Long roleId, String authority) {
        this.roleId = roleId;
        this.authority = authority;
    }

    @Override
    public RoleDTO setRoleId(Long roleId) {
        this.roleId = roleId;
        return this;
    }

    @Override
    public Long getRoleId() {
        return roleId;
    }

    @Override
    public RoleDTO setAuthority(String authority) {
        this.authority = authority;
        return this;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}

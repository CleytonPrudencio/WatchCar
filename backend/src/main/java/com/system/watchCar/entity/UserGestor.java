package com.system.watchCar.entity;

import com.system.watchCar.interfaces.IGestorSecurity;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_GESTOR")
@PrimaryKeyJoinColumn(name = "user_id")
public class Gestor extends Agente implements IGestorSecurity {

    private String department;
    private String cargo;

    public Gestor() {
    }

    @Override
    public Gestor setDepartment(String department) {
        this.department = department;
        return this;
    }

    @Override
    public String getDepartment() {
        return department;
    }

    @Override
    public String getCargo() {
        return cargo;
    }

    @Override
    public Gestor setCargo(String cargo) {
        this.cargo = cargo;
        return this;
    }
}

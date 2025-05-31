package com.system.watchCar.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Entity
@Table(name = "TB_USER")
public class User extends UserSimple {

    @OneToMany(mappedBy = "user")
    private List<Veiculo> carros = new ArrayList<>();

    @OneToOne
    private Local local;

    public User() {}

    public User(Local local) {
        this.local = local;
    }

    public List<Veiculo> getCarros() {
        return carros;
    }

    public void setCarros(List<Veiculo> carros) {
        this.carros = carros;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }
}

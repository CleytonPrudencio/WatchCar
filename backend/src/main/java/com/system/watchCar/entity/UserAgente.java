package com.system.watchCar.entity;

import com.system.watchCar.interfaces.IAgente;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_AGENTE")
public class Agente extends UserSimple implements IAgente {

    private String delegate;
    private String badge;
    private String ra;

    public Agente() {
    }

    @Override
    public Agente setDelegate(String delegate) {
        this.delegate = delegate;
        return this;
    }

    @Override
    public String getDelegate() {
        return delegate;
    }

    @Override
    public Agente setBadge(String badge) {
        this.badge = badge;
        return this;
    }

    @Override
    public String getBadge() {
        return badge;
    }

    @Override
    public Agente setRa(String ra) {
        this.ra = ra;
        return this;
    }

    @Override
    public String getRa() {
        return ra;
    }
}

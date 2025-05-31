package com.system.watchCar.enums;

public enum VeiculoType {

    CARRO("Carro"),
    MOTO("Moto"),
    CAMINHAO("Caminhão"),
    ONIBUS("Ônibus"),
    VAN("Van");

    private final String descricao;

    VeiculoType(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}


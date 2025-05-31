package com.system.watchCar.enums;

public enum TipoVeiculo {

    CARRO("Carro"),
    MOTO("Moto"),
    CAMINHAO("Caminhão"),
    ONIBUS("Ônibus"),
    VAN("Van");

    private final String descricao;

    TipoVeiculo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}


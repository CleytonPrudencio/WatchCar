package com.system.watchCar.dto.requests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.system.watchCar.enums.VeiculoType;
import com.system.watchCar.interfaces.IUserSimple;
import com.system.watchCar.interfaces.IVeiculo;

public class VeiculoRequest implements IVeiculo {

    private Long idVeiculo;
    private String marcaVeiculo;
    private String modeloVeiculo;
    private int anoVeiculo;
    private String placaVeiculo;
    private String corVeiculo;
    private VeiculoType veiculoType;

    public VeiculoRequest() {
    }

    @Override
    public VeiculoRequest setIdVeiculo(Long id) {
        this.idVeiculo = id;
        return this;
    }

    @JsonProperty("id")
    @Override
    public Long getIdVeiculo() {
        return idVeiculo;
    }

    @Override
    public VeiculoRequest setMarcaVeiculo(String marca) {
        this.marcaVeiculo = marca;
        return this;
    }

    @JsonProperty("marca")
    @Override
    public String getMarcaVeiculo() {
        return marcaVeiculo;
    }

    @Override
    public VeiculoRequest setModeloVeiculo(String modelo) {
        this.modeloVeiculo = modelo;
        return this;
    }

    @JsonProperty("modelo")
    @Override
    public String getModeloVeiculo() {
        return modeloVeiculo;
    }

    @Override
    public VeiculoRequest setAnoVeiculo(int ano) {
        this.anoVeiculo = ano;
        return this;
    }

    @JsonProperty("ano")
    @Override
    public int getAnoVeiculo() {
        return anoVeiculo;
    }

    @Override
    public VeiculoRequest setPlacaVeiculo(String placa) {
        this.placaVeiculo = placa;
        return this;
    }

    @JsonProperty("placa")
    @Override
    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    @Override
    public VeiculoRequest setCorVeiculo(String cor) {
        this.corVeiculo = cor;
        return this;
    }

    @JsonProperty("cor")
    @Override
    public String getCorVeiculo() {
        return corVeiculo;
    }

    @Override
    public VeiculoRequest setTipoVeiculo(VeiculoType tipo) {
        this.veiculoType = tipo;
        return this;
    }

    @JsonProperty("tipo")
    @Override
    public VeiculoType getTipoVeiculo() {
        return veiculoType;
    }

    @JsonIgnore
    @Override
    public <U extends IUserSimple> IVeiculo setUser(U user) {
        return null;
    }

    @Override
    public IUserSimple getUser() {
        return null;
    }
}

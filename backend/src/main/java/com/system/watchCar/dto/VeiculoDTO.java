package com.system.watchCar.dto;

import com.system.watchCar.entity.User;
import com.system.watchCar.enums.VeiculoType;
import com.system.watchCar.interfaces.IUserSimple;
import com.system.watchCar.interfaces.IVeiculo;

public class VeiculoDTO implements IVeiculo {

    private Long idVeiculo;
    private String marcaVeiculo;
    private String modeloVeiculo;
    private int anoVeiculo;
    private String placaVeiculo;
    private String corVeiculo;
    private VeiculoType veiculoType;
    private User user;

    public VeiculoDTO() {
    }

    @Override
    public VeiculoDTO setIdVeiculo(Long id) {
        this.idVeiculo = id;
        return this;
    }

    @Override
    public Long getIdVeiculo() {
        return idVeiculo;
    }

    @Override
    public VeiculoDTO setMarcaVeiculo(String marca) {
        this.marcaVeiculo = marca;
        return this;
    }

    @Override
    public String getMarcaVeiculo() {
        return marcaVeiculo;
    }

    @Override
    public VeiculoDTO setModeloVeiculo(String modelo) {
        this.modeloVeiculo = modelo;
        return this;
    }

    @Override
    public String getModeloVeiculo() {
        return modeloVeiculo;
    }

    @Override
    public VeiculoDTO setAnoVeiculo(int ano) {
        this.anoVeiculo = ano;
        return this;
    }

    @Override
    public int getAnoVeiculo() {
        return anoVeiculo;
    }

    @Override
    public VeiculoDTO setPlacaVeiculo(String placa) {
        this.placaVeiculo = placa;
        return this;
    }

    @Override
    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    @Override
    public VeiculoDTO setCorVeiculo(String cor) {
        this.corVeiculo = cor;
        return this;
    }

    @Override
    public String getCorVeiculo() {
        return corVeiculo;
    }

    @Override
    public VeiculoDTO setTipoVeiculo(VeiculoType tipo) {
        this.veiculoType = tipo;
        return this;
    }

    @Override
    public VeiculoType getTipoVeiculo() {
        return veiculoType;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public IVeiculo setUser(IUserSimple user) {
        this.user = user.toUserSimple(User.class);
        return this;
    }
}


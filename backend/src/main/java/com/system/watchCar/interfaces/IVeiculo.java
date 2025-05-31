package com.system.watchCar.interfaces;

import com.system.watchCar.enums.TipoVeiculo;

public interface IVeiculo {

    IVeiculo setIdVeiculo(Long id);
    Long getIdVeiculo();

    IVeiculo setMarcaVeiculo(String marca);
    String getMarcaVeiculo();

    IVeiculo setModeloVeiculo(String modelo);
    String getModeloVeiculo();

    IVeiculo setAnoVeiculo(int ano);
    int getAnoVeiculo();

    IVeiculo setPlacaVeiculo(String placa);
    String getPlacaVeiculo();

    IVeiculo setCorVeiculo(String cor);
    String getCorVeiculo();

    IVeiculo setTipoVeiculo(TipoVeiculo tipo);
    TipoVeiculo getTipoVeiculo();

    <U extends IUserSimple> IVeiculo setUser(U user);
    IUserSimple getUser();
}

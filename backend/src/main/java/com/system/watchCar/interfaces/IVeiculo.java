package com.system.watchCar.interfaces;

import com.system.watchCar.enums.VeiculoType;

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

    IVeiculo setTipoVeiculo(VeiculoType tipo);
    VeiculoType getTipoVeiculo();

    <U extends IUserSimple> IVeiculo setUser(U user);
    IUserSimple getUser();

    default <V extends IVeiculo> V toVeiculo(Class<V> clazz) {
        try {
            V instance = clazz.getDeclaredConstructor().newInstance();
            instance.setIdVeiculo(getIdVeiculo());
            instance.setMarcaVeiculo(getMarcaVeiculo());
            instance.setModeloVeiculo(getModeloVeiculo());
            instance.setAnoVeiculo(getAnoVeiculo());
            instance.setPlacaVeiculo(getPlacaVeiculo());
            instance.setCorVeiculo(getCorVeiculo());
            instance.setTipoVeiculo(getTipoVeiculo());
            instance.setUser(getUser());
            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter o veículo para classe " + clazz.getSimpleName(), e);
        }
    }
}

package com.system.watchCar.interfaces;

import com.system.watchCar.service.exceptions.LocalException;

public interface ILocal {

    ILocal setIdLocal(Long id);
    Long getIdLocal();

    ILocal setLogradouro(String logradouro);
    String getLogradouro();

    ILocal setBairro(String bairro);
    String getBairro();

    ILocal setCidade(String cidade);
    String getCidade();

    ILocal setEstado(String estado);
    String getEstado();

    ILocal setCep(String cep);
    String getCep();

    default <L extends ILocal> L toLocal(Class<L> clazz) {
        try {
            L local = clazz.getDeclaredConstructor().newInstance();
            local.setIdLocal(getIdLocal());
            local.setLogradouro(getLogradouro());
            local.setBairro(getBairro());
            local.setCidade(getCidade());
            local.setEstado(getEstado());
            local.setCep(getCep());
            return local;
        } catch (Exception e) {
            throw new LocalException("Erro ao converter para Logradouro: " + clazz.getSimpleName());
        }
    }
}

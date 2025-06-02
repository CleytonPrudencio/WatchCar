package com.system.watchCar.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.system.watchCar.interfaces.ILocal;

public class LocalRequest implements ILocal {

    private Long idLocal;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    public LocalRequest() {
    }

    @Override
    public LocalRequest setIdLocal(Long id) {
        this.idLocal = id;
        return this;
    }

    @JsonProperty("id")
    @Override
    public Long getIdLocal() {
        return idLocal;
    }

    @Override
    public LocalRequest setLogradouro(String logradouro) {
        this.logradouro = logradouro;
        return this;
    }

    @Override
    public String getLogradouro() {
        return logradouro;
    }

    @Override
    public LocalRequest setBairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    @Override
    public String getBairro() {
        return bairro;
    }

    @Override
    public LocalRequest setCidade(String cidade) {
        this.cidade = cidade;
        return this;
    }

    @Override
    public String getCidade() {
        return cidade;
    }

    @Override
    public LocalRequest setEstado(String estado) {
        this.estado = estado;
        return this;
    }

    @Override
    public String getEstado() {
        return estado;
    }

    @Override
    public LocalRequest setCep(String cep) {
        this.cep = cep;
        return this;
    }

    @Override
    public String getCep() {
        return cep;
    }
}

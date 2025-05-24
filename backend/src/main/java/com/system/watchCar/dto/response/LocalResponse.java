package com.system.watchCar.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.system.watchCar.interfaces.ILocal;
import com.system.watchCar.interfaces.IResponseOK;

import javax.persistence.Column;
import java.util.Objects;

public class LocalResponse implements ILocal, IResponseOK {
    private Long idLocal;
    private String logradouro;
    private String bairro;
    private String cidade;
    @Column(name = "uf")
    private String estado;
    private String cep;

    public LocalResponse() {
    }

    @Override
    public LocalResponse setIdLocal(Long id) {
        this.idLocal = id;
        return this;
    }

    @Override
    @JsonProperty("id")
    public Long getIdLocal() {
        return idLocal;
    }

    @Override
    public LocalResponse setLogradouro(String logradouro) {
        this.logradouro = logradouro;
        return this;
    }

    @Override
    public String getLogradouro() {
        return logradouro;
    }

    @Override
    public LocalResponse setBairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    @Override
    public String getBairro() {
        return bairro;
    }

    @Override
    @JsonProperty("localidade")
    public LocalResponse setCidade(String cidade) {
        this.cidade = cidade;
        return this;
    }

    @Override
    public String getCidade() {
        return cidade;
    }

    @Override
    public LocalResponse setEstado(String estado) {
        this.estado = estado;
        return this;
    }

    @Override
    public String getEstado() {
        return estado;
    }

    @Override
    public LocalResponse setCep(String cep) {
        this.cep = cep;
        return this;
    }

    @Override
    public String getCep() {
        return cep;
    }

    @Override
    public boolean getSuccess() {
        return Objects.nonNull(cep);
    }
}

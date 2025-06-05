package com.system.watchCar.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.system.watchCar.interfaces.IOcorrenciaTipo;

public class OcorrenciaTipoResponse implements IOcorrenciaTipo {

    private Long idTipoOcorrencia;
    private String nameTipoOcorrencia;
    private String descricaoTipoOcorrencia;

    public OcorrenciaTipoResponse() {
    }

    @Override
    public OcorrenciaTipoResponse setIdTipoOcorrencia(Long id) {
        this.idTipoOcorrencia = id;
        return this;
    }

    @JsonProperty("id")
    @Override
    public Long getIdTipoOcorrencia() {
        return idTipoOcorrencia;
    }

    @Override
    public OcorrenciaTipoResponse setNameTipoOcorrencia(String name) {
        this.nameTipoOcorrencia = name;
        return this;
    }

    @JsonProperty("name")
    @Override
    public String getNameTipoOcorrencia() {
        return nameTipoOcorrencia;
    }

    @Override
    public OcorrenciaTipoResponse setDescricaoTipoOcorrencia(String descricao) {
        this.descricaoTipoOcorrencia = descricao;
        return this;
    }

    @JsonProperty("description")
    @Override
    public String getDescricaoTipoOcorrencia() {
        return descricaoTipoOcorrencia;
    }
}

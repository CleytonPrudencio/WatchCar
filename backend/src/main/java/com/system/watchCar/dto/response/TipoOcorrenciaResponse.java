package com.system.watchCar.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.system.watchCar.interfaces.ITipoOcorrencia;

public class TipoOcorrenciaResponse implements ITipoOcorrencia {

    private Long idTipoOcorrencia;
    private String nameTipoOcorrencia;
    private String descricaoTipoOcorrencia;

    public TipoOcorrenciaResponse() {
    }

    @Override
    public TipoOcorrenciaResponse setIdTipoOcorrencia(Long id) {
        this.idTipoOcorrencia = id;
        return this;
    }

    @JsonProperty("id")
    @Override
    public Long getIdTipoOcorrencia() {
        return idTipoOcorrencia;
    }

    @Override
    public TipoOcorrenciaResponse setNameTipoOcorrencia(String name) {
        this.nameTipoOcorrencia = name;
        return this;
    }

    @JsonProperty("name")
    @Override
    public String getNameTipoOcorrencia() {
        return nameTipoOcorrencia;
    }

    @Override
    public TipoOcorrenciaResponse setDescricaoTipoOcorrencia(String descricao) {
        this.descricaoTipoOcorrencia = descricao;
        return this;
    }

    @JsonProperty("description")
    @Override
    public String getDescricaoTipoOcorrencia() {
        return descricaoTipoOcorrencia;
    }
}

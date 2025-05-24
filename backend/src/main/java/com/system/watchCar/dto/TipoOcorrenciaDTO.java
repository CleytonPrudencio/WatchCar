package com.system.watchCar.dto;

import com.system.watchCar.interfaces.ITipoOcorrencia;

public class TipoOcorrenciaDTO implements ITipoOcorrencia {

    private Long idTipoOcorrencia;
    private String nameTipoOcorrencia;
    private String descricaoTipoOcorrencia;

    public TipoOcorrenciaDTO() {
    }

    @Override
    public TipoOcorrenciaDTO setIdTipoOcorrencia(Long id) {
        this.idTipoOcorrencia = id;
        return this;
    }

    @Override
    public Long getIdTipoOcorrencia() {
        return idTipoOcorrencia;
    }

    @Override
    public TipoOcorrenciaDTO setNameTipoOcorrencia(String name) {
        this.nameTipoOcorrencia = name;
        return this;
    }

    @Override
    public String getNameTipoOcorrencia() {
        return nameTipoOcorrencia;
    }

    @Override
    public TipoOcorrenciaDTO setDescricaoTipoOcorrencia(String descricao) {
        this.descricaoTipoOcorrencia = descricao;
        return this;
    }

    @Override
    public String getDescricaoTipoOcorrencia() {
        return descricaoTipoOcorrencia;
    }
}

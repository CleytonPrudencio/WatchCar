package com.system.watchCar.dto;

import com.system.watchCar.interfaces.IOcorrenciaTipo;

public class OcorrenciaTipoDTO implements IOcorrenciaTipo {

    private Long idTipoOcorrencia;
    private String nameTipoOcorrencia;
    private String descricaoTipoOcorrencia;

    public OcorrenciaTipoDTO() {
    }

    @Override
    public OcorrenciaTipoDTO setIdTipoOcorrencia(Long id) {
        this.idTipoOcorrencia = id;
        return this;
    }

    @Override
    public Long getIdTipoOcorrencia() {
        return idTipoOcorrencia;
    }

    @Override
    public OcorrenciaTipoDTO setNameTipoOcorrencia(String name) {
        this.nameTipoOcorrencia = name;
        return this;
    }

    @Override
    public String getNameTipoOcorrencia() {
        return nameTipoOcorrencia;
    }

    @Override
    public OcorrenciaTipoDTO setDescricaoTipoOcorrencia(String descricao) {
        this.descricaoTipoOcorrencia = descricao;
        return this;
    }

    @Override
    public String getDescricaoTipoOcorrencia() {
        return descricaoTipoOcorrencia;
    }
}

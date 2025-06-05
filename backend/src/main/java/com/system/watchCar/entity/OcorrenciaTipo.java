package com.system.watchCar.entity;

import com.system.watchCar.interfaces.IOcorrenciaTipo;
import jakarta.persistence.*;

@Entity
@Table(name = "TB_TIPO_OCORRENCIA")
public class OcorrenciaTipo implements IOcorrenciaTipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoOcorrencia;
    private String nameTipoOcorrencia;
    private String descricaoTipoOcorrencia;

    public OcorrenciaTipo() {
    }

    @Override
    public OcorrenciaTipo setIdTipoOcorrencia(Long id) {
        this.idTipoOcorrencia = id;
        return this;
    }

    @Override
    public Long getIdTipoOcorrencia() {
        return idTipoOcorrencia;
    }

    @Override
    public OcorrenciaTipo setNameTipoOcorrencia(String name) {
        this.nameTipoOcorrencia = name;
        return this;
    }

    @Override
    public String getNameTipoOcorrencia() {
        return nameTipoOcorrencia;
    }

    @Override
    public OcorrenciaTipo setDescricaoTipoOcorrencia(String descricao) {
        this.descricaoTipoOcorrencia = descricao;
        return this;
    }

    @Override
    public String getDescricaoTipoOcorrencia() {
        return descricaoTipoOcorrencia;
    }
}

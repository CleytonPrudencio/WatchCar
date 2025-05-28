package com.system.watchCar.entity;

import com.system.watchCar.interfaces.ITipoOcorrencia;
import jakarta.persistence.*;

@Entity
@Table(name = "TB_TIPO_OCORRENCIA")
public class TipoOcorrencia implements ITipoOcorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTipoOcorrencia;
    private String nameTipoOcorrencia;
    private String descricaoTipoOcorrencia;

    public TipoOcorrencia() {
    }

    @Override
    public TipoOcorrencia setIdTipoOcorrencia(Long id) {
        this.idTipoOcorrencia = id;
        return this;
    }

    @Override
    public Long getIdTipoOcorrencia() {
        return idTipoOcorrencia;
    }

    @Override
    public TipoOcorrencia setNameTipoOcorrencia(String name) {
        this.nameTipoOcorrencia = name;
        return this;
    }

    @Override
    public String getNameTipoOcorrencia() {
        return nameTipoOcorrencia;
    }

    @Override
    public TipoOcorrencia setDescricaoTipoOcorrencia(String descricao) {
        this.descricaoTipoOcorrencia = descricao;
        return this;
    }

    @Override
    public String getDescricaoTipoOcorrencia() {
        return descricaoTipoOcorrencia;
    }
}

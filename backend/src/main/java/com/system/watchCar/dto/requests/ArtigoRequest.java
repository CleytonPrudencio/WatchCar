package com.system.watchCar.dto.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.system.watchCar.interfaces.IArtigo;
import jakarta.persistence.*;

public class ArtigoRequest implements IArtigo {

    private Long idArtigo;
    private String codArtigo;
    private String descricaoArtigo;

    public ArtigoRequest() {
    }

    @Override
    public ArtigoRequest setIdArtigo(Long id) {
        this.idArtigo = id;
        return this;
    }

    @JsonProperty("id")
    @Override
    public Long getIdArtigo() {
        return idArtigo;
    }

    @Override
    public ArtigoRequest setCodArtigo(String cod) {
        this.codArtigo = cod;
        return this;
    }

    @Override
    public String getCodArtigo() {
        return codArtigo;
    }

    @Override
    public ArtigoRequest setDescricaoArtigo(String descricao) {
        this.descricaoArtigo = descricao;
        return this;
    }

    @JsonProperty("description")
    @Override
    public String getDescricaoArtigo() {
        return descricaoArtigo;
    }
}


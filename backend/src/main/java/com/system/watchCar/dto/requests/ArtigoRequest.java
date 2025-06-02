package com.system.watchCar.entity;

import com.system.watchCar.interfaces.IArtigo;
import jakarta.persistence.*;

@Entity
@Table(name = "TB_ARTIGOS_CRIMINAIS")
public class Artigo implements IArtigo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idArtigo;
    private String codArtigo;
    private String descricaoArtigo;

    public Artigo() {
    }

    @Override
    public Artigo setIdArtigo(Long id) {
        this.idArtigo = id;
        return this;
    }

    @Override
    public Long getIdArtigo() {
        return idArtigo;
    }

    @Override
    public Artigo setCodArtigo(String cod) {
        this.codArtigo = cod;
        return this;
    }

    @Override
    public String getCodArtigo() {
        return codArtigo;
    }

    @Override
    public Artigo setDescricaoArtigo(String descricao) {
        this.descricaoArtigo = descricao;
        return this;
    }

    @Override
    public String getDescricaoArtigo() {
        return descricaoArtigo;
    }
}


package com.system.watchCar.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "TB_ARTIGOS_CRIMINAIS")
public class Artigo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "COD_ARTIGO")
    private String codArtigo;

    @Column(name = "DESCRICAO")
    private String descricao;
}


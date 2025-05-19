package com.system.watchCar.entity;

import com.system.watchCar.interfaces.ILocal;

import javax.persistence.*;

@Entity
@Table(name = "TB_LOCAL")
public class Local implements ILocal {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "localizacao_seq")
    @javax.persistence.SequenceGenerator(name = "localizacao_seq", sequenceName = "ISEQ$$_76242", allocationSize = 1)
    private Long idLocal;

    @Column(length = 255)
    private String logradouro;

    @Column(length = 255)
    private String bairro;

    @Column(length = 255)
    private String cidade;

    @Column(length = 255)
    private String estado;

    @Column(length = 255)
    private String cep;

    public Local() {
    }

    @Override
    public Local setIdLocal(Long id) {
        this.idLocal = id;
        return this;
    }

    @Override
    public Long getIdLocal() {
        return idLocal;
    }

    @Override
    public Local setLogradouro(String logradouro) {
        this.logradouro = logradouro;
        return this;
    }

    @Override
    public String getLogradouro() {
        return logradouro;
    }

    @Override
    public Local setBairro(String bairro) {
        this.bairro = bairro;
        return this;
    }

    @Override
    public String getBairro() {
        return bairro;
    }

    @Override
    public Local setCidade(String cidade) {
        this.cidade = cidade;
        return this;
    }

    @Override
    public String getCidade() {
        return cidade;
    }

    @Override
    public Local setEstado(String estado) {
        this.estado = estado;
        return this;
    }

    @Override
    public String getEstado() {
        return estado;
    }

    @Override
    public Local setCep(String cep) {
        this.cep = cep;
        return this;
    }

    @Override
    public String getCep() {
        return cep;
    }
}

package com.system.watchCar.entity;

import com.system.watchCar.enums.TipoVeiculo;
import com.system.watchCar.interfaces.IUserSimple;
import com.system.watchCar.interfaces.IVeiculo;
import jakarta.persistence.*;

@Entity
@Table(name = "TB_VEICULO")
public class Veiculo implements IVeiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVeiculo;
    private String marcaVeiculo;
    private String modeloVeiculo;
    private int anoVeiculo;
    private String placaVeiculo;
    private String corVeiculo;

    @Enumerated(EnumType.STRING)
    private TipoVeiculo tipoVeiculo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Veiculo() {
    }

    @Override
    public Veiculo setIdVeiculo(Long id) {
        this.idVeiculo = id;
        return this;
    }

    @Override
    public Long getIdVeiculo() {
        return idVeiculo;
    }

    @Override
    public Veiculo setMarcaVeiculo(String marca) {
        this.marcaVeiculo = marca;
        return this;
    }

    @Override
    public String getMarcaVeiculo() {
        return marcaVeiculo;
    }

    @Override
    public Veiculo setModeloVeiculo(String modelo) {
        this.modeloVeiculo = modelo;
        return this;
    }

    @Override
    public String getModeloVeiculo() {
        return modeloVeiculo;
    }

    @Override
    public Veiculo setAnoVeiculo(int ano) {
        this.anoVeiculo = ano;
        return this;
    }

    @Override
    public int getAnoVeiculo() {
        return anoVeiculo;
    }

    @Override
    public Veiculo setPlacaVeiculo(String placa) {
        this.placaVeiculo = placa;
        return this;
    }

    @Override
    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    @Override
    public Veiculo setCorVeiculo(String cor) {
        this.corVeiculo = cor;
        return this;
    }

    @Override
    public String getCorVeiculo() {
        return corVeiculo;
    }

    @Override
    public Veiculo setTipoVeiculo(TipoVeiculo tipo) {
        this.tipoVeiculo = tipo;
        return this;
    }

    @Override
    public TipoVeiculo getTipoVeiculo() {
        return tipoVeiculo;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public IVeiculo setUser(IUserSimple user) {
        this.user = user.toUserSimple(User.class);
        return this;
    }
}


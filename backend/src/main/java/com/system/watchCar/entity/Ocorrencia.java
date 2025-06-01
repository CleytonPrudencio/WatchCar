package com.system.watchCar.entity;

import com.system.watchCar.enums.OcorrenciaStatus;
import com.system.watchCar.interfaces.*;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_OCORRENCIA")
public class Ocorrencia implements IOcorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOcorrencia;
    private LocalDate dataOcorrencia;
    private String descricaoOcorrencia;
    private LocalDateTime dataHoraOcorrencia;

    @ManyToOne
    @JoinColumn(name = "local_id")
    private Local localOcorrencia;

    @ManyToOne
    @JoinColumn(name = "gestor_id")
    private UserGestor gestorSecurity;

    @ManyToOne
    @JoinColumn(name = "local_ocorrencia_id")
    private Local localDaOcorrencia;

    @ManyToOne
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculoOcorrencia;

    @ManyToOne
    @JoinColumn(name = "tipo_ocorrencia_id")
    private OcorrenciaTipo tipoOcorrencia;

    private OcorrenciaStatus statusOcorrencia;

    public Ocorrencia() {
    }

    @Override
    public Ocorrencia setIdOcorrencia(Long id) {
        this.idOcorrencia = id;
        return this;
    }

    @Override
    public Long getIdOcorrencia() {
        return idOcorrencia;
    }

    @Override
    public Ocorrencia setDataOcorrencia(LocalDate name) {
        return null;
    }

    @Override
    public LocalDate getDataOcorrencia() {
        return dataOcorrencia;
    }

    @Override
    public Ocorrencia setDescricaoOcorrencia(String descricao) {
        this.descricaoOcorrencia = descricao;
        return this;
    }

    @Override
    public String getDescricaoOcorrencia() {
        return descricaoOcorrencia;
    }

    @Override
    public Ocorrencia setDataHoraOcorrencia(LocalDateTime dataHora) {
        this.dataHoraOcorrencia = dataHora;
        return this;
    }

    @Override
    public LocalDateTime getDataHoraOcorrencia() {
        return dataHoraOcorrencia;
    }

    @Override
    public <L extends ILocal> Ocorrencia setLocalOcorrencia(L local) {
        this.localOcorrencia = local.toLocal(Local.class);
        return this;
    }

    @Override
    public Local getLocalOcorrencia() {
        return localOcorrencia;
    }

    @Override
    public <U extends IGestorSecurity> Ocorrencia setGestorSecurity(U gestor) {
        this.gestorSecurity = gestor.toGestor(UserGestor.class);
        return this;
    }

    @Override
    public UserGestor getGestorSecurity() {
        return gestorSecurity;
    }

    @Override
    public <L extends ILocal> Ocorrencia setLocalDaOcorrencia(L local) {
        this.localDaOcorrencia = local.toLocal(Local.class);
        return this;
    }

    @Override
    public Local getLocalDaOcorrencia() {
        return localDaOcorrencia;
    }

    @Override
    public <V extends IVeiculo> Ocorrencia setVeiculoOcorrencia(V veiculo) {
        this.veiculoOcorrencia = veiculo.toVeiculo(Veiculo.class);
        return this;
    }

    @Override
    public Veiculo getVeiculoOcorrencia() {
        return veiculoOcorrencia;
    }

    @Override
    public IOcorrencia setTipoOcorrencia(IOcorrenciaTipo tipo) {
        tipoOcorrencia = tipo.toTipoOcorrencia(OcorrenciaTipo.class);
        return this;
    }

    @Override
    public OcorrenciaTipo getTipoOcorrencia() {
        return tipoOcorrencia;
    }

    @Override
    public Ocorrencia setStatusOcorrencia(OcorrenciaStatus status) {
        this.statusOcorrencia = status;
        return this;
    }

    @Override
    public OcorrenciaStatus getStatusOcorrencia() {
        return statusOcorrencia;
    }
}

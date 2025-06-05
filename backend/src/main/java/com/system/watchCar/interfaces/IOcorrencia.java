package com.system.watchCar.interfaces;

import com.system.watchCar.enums.OcorrenciaStatus;
import com.system.watchCar.service.exceptions.OcorrenciaException;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

public interface IOcorrencia {

    IOcorrencia setIdOcorrencia(Long id);
    Long getIdOcorrencia();

    IOcorrencia setDataOcorrencia(LocalDate data);
    LocalDate getDataOcorrencia();

    IOcorrencia setDescricaoOcorrencia(String descricao);
    String getDescricaoOcorrencia();

    IOcorrencia setDataHoraOcorrencia(LocalDateTime dataHora);
    LocalDateTime getDataHoraOcorrencia();

    <L extends ILocal> IOcorrencia setLocalOcorrencia(L local);
    <L extends ILocal> L getLocal();

    <U extends IGestorSecurity> IOcorrencia setGestorSecurity(U gestor);
    <U extends IGestorSecurity> U getGestorSecurity();

    <U extends IUserSimple> U getDenunciante();
    <U extends IUserSimple> IOcorrencia setDenunciante(U denunciante);

    <L extends ILocal> IOcorrencia setLocalDaOcorrencia(L local);
    <L extends ILocal> L getLocalDaOcorrencia();

    <V extends IVeiculo> IOcorrencia addVeiculosOcorrencia(V veiculo);
    <V extends IVeiculo> Collection<V> getVeiculosOcorrencia();

    <T extends IOcorrenciaTipo> IOcorrencia setTipoOcorrencia(T tipo);
    <T extends IOcorrenciaTipo> T getTipoOcorrencia();

    @Enumerated(EnumType.STRING)
    IOcorrencia setStatusOcorrencia(OcorrenciaStatus status);
    OcorrenciaStatus getStatusOcorrencia();

    IOcorrencia setArtigo(IArtigo artigo);
    IArtigo getArtigo();

    default <C extends IOcorrencia> C toOcorrencia(Class<C> clazz) {
        try {
            C instance = clazz.getDeclaredConstructor().newInstance();
            instance
                    .setIdOcorrencia(getIdOcorrencia())
                    .setDataOcorrencia(getDataOcorrencia())
                    .setDescricaoOcorrencia(getDescricaoOcorrencia())
                    .setDataHoraOcorrencia(getDataHoraOcorrencia())
                    .setLocalOcorrencia(getLocal())
                    .setGestorSecurity(getGestorSecurity())
                    .setLocalDaOcorrencia(getLocalDaOcorrencia())
                    .setTipoOcorrencia(getTipoOcorrencia())
                    .setStatusOcorrencia(getStatusOcorrencia())
                    .setArtigo(getArtigo());
            for(IVeiculo veiculo : getVeiculosOcorrencia()) {
                        instance.addVeiculosOcorrencia(veiculo);
            }
            return instance;
        } catch (Exception e) {
            throw new OcorrenciaException("Error converting to Ocorrencia: " + clazz.getSimpleName(), e);
        }
    }
}

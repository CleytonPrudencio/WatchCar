package com.system.watchCar.interfaces;

import com.system.watchCar.enums.OcorrenciaStatus;
import com.system.watchCar.service.exceptions.OcorrenciaException;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface IOcorrencia {

    IOcorrencia setIdOcorrencia(Long id);

    Long getIdOcorrencia();

    IOcorrencia setDataOcorrencia(LocalDate name);

    LocalDate getDataOcorrencia();

    IOcorrencia setDescricaoOcorrencia(String descricao);

    String getDescricaoOcorrencia();

    IOcorrencia setDataHoraOcorrencia(LocalDateTime dataHora);

    LocalDateTime getDataHoraOcorrencia();

    <L extends ILocal> IOcorrencia setLocalOcorrencia(L local);

    <L extends ILocal> L getLocalOcorrencia();

    <U extends IGestorSecurity> IOcorrencia setGestorSecurity(U gestor);

    <U extends IGestorSecurity> U getGestorSecurity();

    <L extends ILocal> IOcorrencia setLocalDaOcorrencia(L local);

    <L extends ILocal> L getLocalDaOcorrencia();

    <V extends IVeiculo> IOcorrencia setVeiculoOcorrencia(V veiculo);

    <V extends IVeiculo> V getVeiculoOcorrencia();

    <T extends IOcorrenciaTipo> IOcorrencia setTipoOcorrencia(T tipo);

    <T extends IOcorrenciaTipo> T getTipoOcorrencia();

    @Enumerated(EnumType.STRING)
    IOcorrencia setStatusOcorrencia(OcorrenciaStatus status);

    OcorrenciaStatus getStatusOcorrencia();

    default <C extends IOcorrencia> C toOcorrencia(Class<C> clazz) {
        try {
            C instance = clazz.getDeclaredConstructor().newInstance();
            instance
                    .setIdOcorrencia(getIdOcorrencia())
                    .setDataOcorrencia(getDataOcorrencia())
                    .setDescricaoOcorrencia(getDescricaoOcorrencia())
                    .setDataHoraOcorrencia(getDataHoraOcorrencia())
                    .setLocalOcorrencia(getLocalOcorrencia())
                    .setGestorSecurity(getGestorSecurity())
                    .setLocalDaOcorrencia(getLocalDaOcorrencia())
                    .setVeiculoOcorrencia(getVeiculoOcorrencia())
                    .setTipoOcorrencia(getTipoOcorrencia())
                    .setStatusOcorrencia(getStatusOcorrencia());
            return instance;
        } catch (Exception e) {
            throw new OcorrenciaException("Error converting to Ocorrencia: " + clazz.getSimpleName(), e);
        }
    }
}

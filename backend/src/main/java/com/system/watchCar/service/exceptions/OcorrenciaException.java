package com.system.watchCar.service.exceptions;

import com.system.watchCar.interfaces.IOcorrencia;
import com.system.watchCar.utils.Msg;

import java.util.Objects;

public class OcorrenciaException extends RuntimeException {
    public OcorrenciaException(String message) {
        super(message);
    }

    public OcorrenciaException(String message, Exception error) {
        super(message, error);
    }

    public static boolean validation(IOcorrencia ocorrencia) {
        if (Objects.isNull(ocorrencia)) throw new OcorrenciaException("Ocorrência não pode ser nula.");
        if (Objects.isNull(ocorrencia.getDataOcorrencia()))
            throw new OcorrenciaException("Data da ocorrência não pode ser nula.");
        if (Objects.isNull(ocorrencia.getDescricaoOcorrencia()) || ocorrencia.getDescricaoOcorrencia().isEmpty()) {
            throw new OcorrenciaException("Faça uma descrição da ocorrência.");
        }
        if (Objects.isNull(ocorrencia.getDataHoraOcorrencia()))
            throw new OcorrenciaException("Escolha a data e hora da ocorrência.");
        if (Objects.isNull(ocorrencia.getLocal()))
            throw new OcorrenciaException("Escolha o local onde foi feita a ocorrência.");
        if (Objects.isNull(ocorrencia.getGestorSecurity()))
            throw new OcorrenciaException("Escolha o gestor de segurança da ocorrência.");
        if (Objects.isNull(ocorrencia.getLocalDaOcorrencia()))
            throw new OcorrenciaException("Escolha o local da ocorrência.");
        if (Objects.isNull(ocorrencia.getVeiculosOcorrencia()))
            throw new OcorrenciaException("Escolha o veículo da envolvido na ocorrência.");
        if (Objects.isNull(ocorrencia.getTipoOcorrencia()))
            throw new OcorrenciaException("Escolha o tipo de ocorrência.");
        if (Objects.isNull(ocorrencia.getStatusOcorrencia()))
            throw new OcorrenciaException("Escolha o status da ocorrência.");
        return true;
    }

    @Override
    public String getMessage() {
        Msg.Erro(this);
        return super.getMessage();
    }
}

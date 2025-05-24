package com.system.watchCar.service.exceptions;

import com.system.watchCar.interfaces.ITipoOcorrencia;
import com.system.watchCar.utils.Msg;

import java.util.Objects;

public class TipoOcorrenciaException extends RuntimeException {
    public TipoOcorrenciaException(String message) {
        super(message);
    }

    public TipoOcorrenciaException(String message, Exception exception) {
        super(message, exception);
    }

    public static boolean validation(ITipoOcorrencia tipoOcorrencia) {
        if (Objects.isNull(tipoOcorrencia)) throw new TipoOcorrenciaException("Tipo de ocorrência nulo");
        if (Objects.isNull(tipoOcorrencia.getNameTipoOcorrencia()) || tipoOcorrencia.getNameTipoOcorrencia().isBlank()) {
            throw new TipoOcorrenciaException("Nome do tipo de ocorrência nulo ou vazio");
        }
        if (Objects.isNull(tipoOcorrencia.getDescricaoTipoOcorrencia()) || tipoOcorrencia.getDescricaoTipoOcorrencia().isBlank()) {
            throw new TipoOcorrenciaException("Faz uma descrição do tipo de ocorrência!");
        }
        return true;
    }

    @Override
    public String getMessage() {
        Msg.Erro(this);
        return super.getMessage();
    }
}

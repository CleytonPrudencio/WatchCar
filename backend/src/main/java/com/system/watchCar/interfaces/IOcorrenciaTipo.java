package com.system.watchCar.interfaces;

import com.system.watchCar.service.exceptions.TipoOcorrenciaException;

public interface IOcorrenciaTipo {

    IOcorrenciaTipo setIdTipoOcorrencia(Long id);
    Long getIdTipoOcorrencia();

    IOcorrenciaTipo setNameTipoOcorrencia(String name);
    String getNameTipoOcorrencia();

    IOcorrenciaTipo setDescricaoTipoOcorrencia(String descricao);
    String getDescricaoTipoOcorrencia();

    default <T extends IOcorrenciaTipo> T toTipoOcorrencia(Class<T> clazz) {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();
            instance.setIdTipoOcorrencia(getIdTipoOcorrencia());
            instance.setNameTipoOcorrencia(getNameTipoOcorrencia());
            instance.setDescricaoTipoOcorrencia(getDescricaoTipoOcorrencia());
            return instance;
        } catch (Exception e) {
            throw new TipoOcorrenciaException("Erro ao converter o tipo de ocorrência para classe "+ clazz.getSimpleName(), e);
        }
    }
}

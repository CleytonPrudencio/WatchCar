package com.system.watchCar.interfaces;

import com.system.watchCar.service.exceptions.TipoOcorrenciaException;

public interface ITipoOcorrencia {

    ITipoOcorrencia setIdTipoOcorrencia(Long id);
    Long getIdTipoOcorrencia();

    ITipoOcorrencia setNameTipoOcorrencia(String name);
    String getNameTipoOcorrencia();

    ITipoOcorrencia setDescricaoTipoOcorrencia(String descricao);
    String getDescricaoTipoOcorrencia();

    default <T extends ITipoOcorrencia> T toTipoOcorrencia(Class<T> clazz) {
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

package com.system.watchCar.service;

import com.system.watchCar.dto.TipoOcorrenciaDTO;
import com.system.watchCar.dto.response.Response;
import com.system.watchCar.dto.response.TipoOcorrenciaResponse;
import com.system.watchCar.entity.TipoOcorrencia;
import com.system.watchCar.repository.TipoOcorrenciaRepository;
import com.system.watchCar.service.exceptions.TipoOcorrenciaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TipoOcorrenciaService {

    @Autowired
    private TipoOcorrenciaRepository tipoOcorrenciaRepository;

    @Transactional
    public TipoOcorrenciaDTO saveTipoOcorrencia(TipoOcorrenciaDTO tipoOcorrencia) {
        TipoOcorrencia tipo = tipoOcorrenciaRepository.save(tipoOcorrencia.toTipoOcorrencia(TipoOcorrencia.class));
        return tipo.toTipoOcorrencia(TipoOcorrenciaDTO.class);
    }

    @Transactional(readOnly = true)
    public Response findAll(){
        List<TipoOcorrencia> tipos = tipoOcorrenciaRepository.findAll();
        if(tipos.isEmpty()){
            throw new TipoOcorrenciaException("Nenhum tipo de ocorrência encontrado");
        }
        Response<List<TipoOcorrenciaResponse>> response = Response.success(tipos.stream()
                .map(tipo -> tipo.toTipoOcorrencia(TipoOcorrenciaResponse.class))
                .toList());
        return response;
    }
}

package com.system.watchCar.service;

import com.system.watchCar.dto.OcorrenciaTipoDTO;
import com.system.watchCar.dto.response.Response;
import com.system.watchCar.dto.response.OcorrenciaTipoResponse;
import com.system.watchCar.entity.OcorrenciaTipo;
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
    public OcorrenciaTipoDTO saveTipoOcorrencia(OcorrenciaTipoDTO tipoOcorrencia) {
        OcorrenciaTipo tipo = tipoOcorrenciaRepository.save(tipoOcorrencia.toTipoOcorrencia(OcorrenciaTipo.class));
        return tipo.toTipoOcorrencia(OcorrenciaTipoDTO.class);
    }

    @Transactional(readOnly = true)
    public Response findAll(){
        List<OcorrenciaTipo> tipos = tipoOcorrenciaRepository.findAll();
        if(tipos.isEmpty()){
            throw new TipoOcorrenciaException("Nenhum tipo de ocorrência encontrado");
        }
        Response<List<OcorrenciaTipoResponse>> response = Response.success(tipos.stream()
                .map(tipo -> tipo.toTipoOcorrencia(OcorrenciaTipoResponse.class))
                .toList());
        return response;
    }
}

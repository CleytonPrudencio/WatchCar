package com.system.watchCar.service;

import com.system.watchCar.dto.TipoOcorrenciaDTO;
import com.system.watchCar.entity.TipoOcorrencia;
import com.system.watchCar.repository.TipoOcorrenciaRepository;
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
    public List<TipoOcorrenciaDTO> findAll(){
        List<TipoOcorrencia> tipos = tipoOcorrenciaRepository.findAll();
        if(tipos.isEmpty()){
            throw new RuntimeException("Nenhum tipo de ocorrência encontrado");
        }
        return tipos.stream()
                .map(tipo -> tipo.toTipoOcorrencia(TipoOcorrenciaDTO.class))
                .toList();
    }
}

package com.system.watchCar.controller;

import com.system.watchCar.dto.TipoOcorrenciaDTO;
import com.system.watchCar.service.TipoOcorrenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tipos_ocorrencias")
public class TipoOcorrenciaController {

    @Autowired
    private TipoOcorrenciaService tipoOcorrenciaService;

    @GetMapping
    public ResponseEntity<List<TipoOcorrenciaDTO>> findAll() {
        return ResponseEntity.ok(tipoOcorrenciaService.findAll());
    }

}

package com.system.watchCar.dto.requests;

import com.system.watchCar.entity.Artigo;
import com.system.watchCar.entity.Ocorrencia;
import com.system.watchCar.entity.OcorrenciaTipo;
import com.system.watchCar.enums.OcorrenciaStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DenunciaRequest {

    private LocalDate data;
    private String descricao;
    private LocalDateTime dataHora;
    private LocalRequest local;
    private Long idResponsavel;
    private UserRequest denunciante;
    private LocalRequest localOcorrencia;
    private List<VeiculoRequest> veiculos = new ArrayList<>();
    private Long idArtigo;
    private Long tipo;
}


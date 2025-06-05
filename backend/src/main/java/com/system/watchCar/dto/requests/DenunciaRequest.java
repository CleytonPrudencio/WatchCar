package com.system.watchCar.dto.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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

    @Schema(description = "Data da criação da ocorrência", required = true, example = "2025-06-01")
    @NotBlank(message = "Data inválida, informe uma data válida")
    private LocalDate data;

    @Schema(description = "Descrição da denúncia", required = true, example = "Denúncia de furto de veículo")
    @NotBlank(message = "Descrição faça um descrição da denúncia")
    private String descricao;
    private LocalDateTime dataHora;
    private LocalRequest local;
    private Long idResponsavel;
    private UserRequest denunciante;
    private LocalRequest localOcorrencia;
    private List<VeiculoRequest> veiculos = new ArrayList<>();

    @Schema(description = "ID do artigo relacionado à denúncia", required = true, example = "1")
    private Long idArtigo;

    @Schema(description = "ID do tipo de denúncia", required = true, example = "1")
    @NotEmpty(message = "Tipo de denúncia inválido, informe um tipo válido")
    private Long tipo;
}


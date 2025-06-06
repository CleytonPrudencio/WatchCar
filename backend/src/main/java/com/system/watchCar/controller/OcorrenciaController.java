package com.system.watchCar.controller;

import com.system.watchCar.dto.*;
import com.system.watchCar.dto.requests.DenunciaRequest;
import com.system.watchCar.dto.requests.UserRequest;
import com.system.watchCar.entity.Ocorrencia;
import com.system.watchCar.entity.User;
import com.system.watchCar.service.OcorrenciaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/ocorrencias")
public class OcorrenciaController {

    @Autowired
    private final OcorrenciaService ocorrenciaService;

    @Operation(summary = "Login to get JWT token", description = "Authenticate user and return JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful login, JWT token returned"),
            @ApiResponse(responseCode = "400", description = "Something went wrong"),
            @ApiResponse(responseCode = "422", description = "Invalid username/password supplied")
    })
    @GetMapping("/listar")
    public Page<OcorrenciaDTO> listarOcorrencias(
            @RequestParam(required = false, defaultValue = "") String status,
            @RequestParam(required = false, defaultValue = "") String artigo,
            @RequestParam(required = false, defaultValue = "") String hora,
            @RequestParam(required = false, defaultValue = "") String usuarioNome,
            @RequestParam(required = false, defaultValue = "") String usuarioEmail,
            @RequestParam(required = false, defaultValue = "") String veiculoMarca,
            @RequestParam(required = false, defaultValue = "") String veiculoModelo,
            @RequestParam(required = false, defaultValue = "") String veiculoPlaca,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        String username = getCurrentUsername();
        // Todo: Implementar lógica para buscar o usuário pelo username
        User user = null;

        return ocorrenciaService.obterOcorrenciasComDetalhes(
                user, status, artigo, hora,
                usuarioNome, usuarioEmail, veiculoMarca, veiculoModelo, veiculoPlaca,
                dataInicio, dataFim, page, size
        );
    }


    @GetMapping("/contagem-por-status")
    public ResponseEntity<Map<String, Long>> contarPorStatus() {
        Map<String, Long> contagem = ocorrenciaService.contarOcorrenciasPorStatus();
        return ResponseEntity.ok(contagem);
    }


    @PostMapping("/criar")
    public ResponseEntity<Ocorrencia> criarDenuncia(@RequestBody DenunciaRequest request) {
        Ocorrencia ocorrenciaCriada = ocorrenciaService.criarDenuncia(request);
        return ResponseEntity.ok(ocorrenciaCriada);
    }

    @GetMapping("/detalhar/{id}")
    public ResponseEntity<OcorrenciaDetalhadaResponse> detalharOcorrencia(@PathVariable Long id) {
        OcorrenciaDetalhadaResponse response = ocorrenciaService.buscarDetalhesPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/responsavel")
    public ResponseEntity<ResponsavelResponse> verificarResponsavel(
            @PathVariable Long id, @RequestParam String usuarioId) {
        boolean isResponsavel = ocorrenciaService.verificarResponsavel(id, usuarioId);
        return ResponseEntity.ok(new ResponsavelResponse(isResponsavel));
    }

    @PutMapping("/{id}/assumir")
    public ResponseEntity<String> assumirResponsavel(
            @PathVariable Long id, @RequestBody UserRequest userRequest) {
        ocorrenciaService.assumirResponsavel(id, String.valueOf(userRequest.getIdUser()));
        return ResponseEntity.ok("Responsabilidade assumida com sucesso");
    }

    @PutMapping("/{id}/desassumir")
    public ResponseEntity<String> desassumirResponsavel(
            @PathVariable Long id, @RequestBody UserRequest userRequest) {
        ocorrenciaService.desassumirResponsavel(id, String.valueOf(userRequest.getIdUser()));
        return ResponseEntity.ok("Responsabilidade desassumida com sucesso");
    }

    @PostMapping("/acao-investigacao")
    public ResponseEntity<String> criarAcao(@RequestBody AcaoInvestigacaoRequest request) {
        ocorrenciaService.salvar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Ação registrada com sucesso.");
    }

    @GetMapping("/acoes-investigacao")
    public ResponseEntity<List<AcaoInvestigacaoDetalhadaResponse>> listarAcoesComDetalhes(
            @RequestParam Long responsavel,
            @RequestParam Long denuncia) {

        List<AcaoInvestigacaoDetalhadaResponse> acoes = ocorrenciaService
                .listarComDetalhes(responsavel, denuncia);

        return ResponseEntity.ok(acoes);
    }

    private String getCurrentUsername() {
        // Obtém a autenticação atual do SecurityContextHolder
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return null;
        }
    }

}

package com.system.watchCar.service;

import com.system.watchCar.dto.AcaoInvestigacaoDetalhadaResponse;
import com.system.watchCar.dto.AcaoInvestigacaoRequest;
import com.system.watchCar.dto.OcorrenciaDTO;
import com.system.watchCar.dto.OcorrenciaDetalhadaResponse;
import com.system.watchCar.dto.requests.DenunciaRequest;
import com.system.watchCar.dto.requests.VeiculoRequest;
import com.system.watchCar.entity.*;
import com.system.watchCar.enums.OcorrenciaStatus;
import com.system.watchCar.enums.TipoTemplateEmail;
import com.system.watchCar.repository.*;
import com.system.watchCar.service.exceptions.UserExecption;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class OcorrenciaService {

    private final OcorrenciaRepository repository;
    private final UserRepository userRepository;
    private final UserGestorRepository userGestorRepository;
    private final VeiculoRepository veiculoRepository;
    private final ResponsavelRepository responsavelRepository;
    private final ArtigoRepository artigoRepository;
    private final AcaoInvestigacaoRepository acaoInvestigacaoRepository;
    private final EmailService emailService;
    private final LocalRepository localRepository;
    private final RoleService roleService;

    private final OcorrenciaRepository ocorrenciaRepository;
    private final OcorrenciaTipoRepository ocorrenciaTipoRepository;


    public OcorrenciaService(OcorrenciaRepository repository, UserRepository userRepository, UserGestorRepository userGestorRepository, VeiculoRepository veiculoRepository, ResponsavelRepository responsavelRepository, ArtigoRepository artigoRepository, AcaoInvestigacaoRepository acaoInvestigacaoRepository, EmailService emailService, LocalRepository localRepository, RoleService roleService, OcorrenciaRepository ocorrenciaRepository, OcorrenciaTipoRepository ocorrenciaTipoRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.userGestorRepository = userGestorRepository;
        this.veiculoRepository = veiculoRepository;
        this.responsavelRepository = responsavelRepository;
        this.artigoRepository = artigoRepository;
        this.acaoInvestigacaoRepository = acaoInvestigacaoRepository;
        this.emailService = emailService;
        this.localRepository = localRepository;
        this.roleService = roleService;
        this.ocorrenciaRepository = ocorrenciaRepository;
        this.ocorrenciaTipoRepository = ocorrenciaTipoRepository;
    }

    @Transactional
    public Page<OcorrenciaDTO> obterOcorrenciasComDetalhes(
            User user, String status, String artigo, String hora,
            String usuarioNome, String usuarioEmail, String veiculoMarca, String veiculoModelo, String veiculoPlaca,
            LocalDateTime dataInicio, LocalDateTime dataFim, int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size);
        Long idUsuario = user.getRoles().stream().toList().get(0).getIdRole(); // Assuming the user has at least one role

        Page<Ocorrencia> ocorrencias = findByFilters(
                status, artigo, hora, usuarioNome, usuarioEmail, veiculoMarca, veiculoModelo, veiculoPlaca,
                dataInicio, dataFim, idUsuario, pageRequest
        );
        // Todo: Mapear Ocorrencia para OcorrenciaDTO
        return new PageImpl<>(null, pageRequest, ocorrencias.getTotalElements());
    }


    public Page<Ocorrencia> findByFilters(
            String status, String artigo, String hora,
            String usuarioNome, String usuarioEmail, String veiculoMarca, String veiculoModelo, String veiculoPlaca,
            LocalDateTime dataInicio, LocalDateTime dataFim,
            Long user, Pageable pageable) {
        List<Predicate> predicates = new ArrayList<>();
        return null;
    }


    @Transactional
    public Ocorrencia criarDenuncia(DenunciaRequest request) {

        // Validar se o usuário responsável existe
        if(request.getIdResponsavel()<1) {
            throw new UserExecption("Responsável inválido");
        }
        UserGestor responsavel = null;
        if (userGestorRepository.findById(request.getIdResponsavel()).isPresent()) {
            responsavel = userGestorRepository.findById(request.getIdResponsavel()).get();
        }


        // Validar se o usuário denunciante existe
        UserExecption.validation(request.getDenunciante());
        User denunciante = userRepository.findByEmail(request.getDenunciante().getEmail())
                .orElseThrow(() -> new UserExecption("Usuário denunciante não encontrado"));

        // Veículo
        if (request.getVeiculos().isEmpty()) {
            throw new UserExecption("Nenhum veículo informado na denúncia");
        }

        // Local da ocorrência
        if (Objects.isNull(request.getLocal())
                || Objects.isNull(request.getLocal().getCep())
                || request.getLocal().getCep().isEmpty()) {
            throw new UserExecption("Local da ocorrência não informado");
        }
        Local localOcorrencia = null;
        if (localRepository.findByCep(request.getLocal().getCep()).isPresent()) {
            localOcorrencia = localRepository.findByCep(request.getLocal().getCep()).get();
        } else {
            localOcorrencia = localRepository.save(localOcorrencia.toLocal(Local.class));
        }

        Ocorrencia ocorrencia = new Ocorrencia();
        ocorrencia.setDataOcorrencia(request.getData());
        ocorrencia.setDescricaoOcorrencia(request.getDescricao());
        ocorrencia.setStatusOcorrencia(OcorrenciaStatus.PENDENTE);

        // Tipo de ocorrência
        OcorrenciaTipo tipoOcorrencia = ocorrenciaTipoRepository.findById(ocorrencia.getIdOcorrencia())
                        .orElseThrow(()-> new UserExecption("Tipo de ocorrência inválido"));
        ocorrencia.setTipoOcorrencia(tipoOcorrencia);

        // Artigo
        if (request.getIdArtigo() == null || request.getIdArtigo() < 1) {
            throw new UserExecption("Artigo inválido");
        }
        Artigo artigo = artigoRepository.findById(request.getIdArtigo())
                .orElseThrow(()-> new UserExecption("Artigo não encontrado"));
        ocorrencia.setArtigo(artigo);

        // Responsável
        ocorrencia.setLocalOcorrencia(request.getLocalOcorrencia());
        ocorrencia.setGestorSecurity(responsavel);

        // Denunciante
        ocorrencia.setDenunciante(denunciante);
        ocorrencia.setDataHoraOcorrencia(request.getDataHora());
        ocorrencia.setLocalDaOcorrencia(localOcorrencia);
        // Veículos
        List<Veiculo> veiculos = new ArrayList<>();
        for (VeiculoRequest veiculo : request.getVeiculos()) {
            veiculos.add(veiculoRepository.save(veiculo.toVeiculo(Veiculo.class)));
        }


        if (true) {

            // Preparar os dados para o template
            Map<String, Object> templateVariables = new HashMap<>();
            templateVariables.put("ocorrencia", ocorrencia);
            templateVariables.put("denunciante", denunciante);

            // Enviar o e-mail com o template
            emailService.enviarEmailComTemplate(
                    denunciante.getEmail(),
                    TipoTemplateEmail.NOVA_DENUNCIA,
                    templateVariables
            );
        }
        return ocorrenciaRepository.save(ocorrencia);
    }


    private boolean isValidStatus(String status) {

        return "Em andamento".equals(status) || "Solucionado".equals(status) || "Arquivado".equals(status);
    }

    public OcorrenciaDetalhadaResponse buscarDetalhesPorId(Long id) {
        OcorrenciaDetalhadaResponse response = new OcorrenciaDetalhadaResponse();
        return response;
    }


    public boolean verificarResponsavel(Long id, String usuarioId) {
        return true;
    }

    @Transactional
    public void assumirResponsavel(Long id, String usuarioId) {
    }

    @Transactional
    public void desassumirResponsavel(Long id, String usuarioId) {
    }


    public List<AcaoInvestigacaoDetalhadaResponse> listarComDetalhes(Long idResponsavel, Long idDenuncia) {
        List<AcaoInvestigacaoDetalhadaResponse> list = new ArrayList<>();
        return list;
    }

    public void salvar(AcaoInvestigacaoRequest request) {

    }

    public Map<String, Long> contarOcorrenciasPorStatus() {
        Map<String, Long> contagem = new HashMap<>();
        return contagem;
    }
}


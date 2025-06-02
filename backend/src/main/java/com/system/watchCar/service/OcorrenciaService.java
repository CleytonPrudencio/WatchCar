package com.system.watchCar.service;

import com.system.watchCar.dto.*;
import com.system.watchCar.dto.requests.DenunciaRequest;
import com.system.watchCar.dto.requests.VeiculoRequest;
import com.system.watchCar.entity.*;
import com.system.watchCar.enums.TipoTemplateEmail;
import com.system.watchCar.enums.VeiculoType;
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
    private final VeiculoRepository veiculoRepository;
    private final ResponsavelRepository responsavelRepository;
    private final ArtigoRepository artigoRepository;
    private final AcaoInvestigacaoRepository acaoInvestigacaoRepository;
    private final EmailService emailService;
    private final LocalRepository localRepository;
    private final RoleService roleService;


    public OcorrenciaService(OcorrenciaRepository repository, UserRepository userRepository, VeiculoRepository veiculoRepository, ResponsavelRepository responsavelRepository, ArtigoRepository artigoRepository, AcaoInvestigacaoRepository acaoInvestigacaoRepository, EmailService emailService, LocalRepository localRepository, RoleService roleService) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.veiculoRepository = veiculoRepository;
        this.responsavelRepository = responsavelRepository;
        this.artigoRepository = artigoRepository;
        this.acaoInvestigacaoRepository = acaoInvestigacaoRepository;
        this.emailService = emailService;
        this.localRepository = localRepository;
        this.roleService = roleService;
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
        UserExecption.validation(request.getResponsavel());
        User responsavel = userRepository.findByEmail(request.getResponsavel().getEmail())
                .orElseThrow(() -> new UserExecption("Usuário responsável não encontrado"));

        // Validar se o usuário denunciante existe
        UserExecption.validation(request.getDenunciante());
        User denunciante = userRepository.findByEmail(request.getDenunciante().getEmail())
                .orElseThrow(() -> new UserExecption("Usuário denunciante não encontrado"));

        // Veículo
        if(request.getVeiculos().isEmpty()){
            throw new UserExecption("Nenhum veículo informado na denúncia");
        }
        // Cadastrado dos veículos
        for(VeiculoRequest veiculo : request.getVeiculos()) {
            Veiculo veiculoEntity = new Veiculo();
            veiculoEntity.setMarcaVeiculo(veiculo.getMarcaVeiculo());
            veiculoEntity.setModeloVeiculo(veiculo.getModeloVeiculo());
            veiculoEntity.setPlacaVeiculo(veiculo.getPlacaVeiculo());
            veiculoEntity.setAnoVeiculo(veiculo.getAnoVeiculo());
            veiculoEntity.setCorVeiculo(veiculo.getCorVeiculo());
            veiculoEntity.setTipoVeiculo(veiculo.getTipoVeiculo());
            veiculoEntity.setUser(denunciante);
            veiculoRepository.save(veiculoEntity);
        }

        // Local da ocorrência
        if (Objects.isNull(request.getLocalOcorrencia())
                || Objects.isNull(request.getLocalOcorrencia().getCep())
                || request.getLocalOcorrencia().getCep().isEmpty()) {
            throw new UserExecption("Local da ocorrência não informado");
        }
        Local local = null;
        if (localRepository.findByCep(request.getLocalOcorrencia().getCep()).isPresent()){
            local = localRepository.findByCep(request.getLocalOcorrencia().getCep()).get();
        }else{
            local.setCep(request.getLocalOcorrencia().getCep());
            local.setCidade(request.getLocalOcorrencia().getCidade());
            local.setBairro(request.getLocalOcorrencia().getBairro());
            local.setEstado(request.getLocalOcorrencia().getEstado());
            local.setLogradouro(request.getLocalOcorrencia().getLogradouro());
            local = localRepository.save(local);
        }

        Ocorrencia ocorrencia = new Ocorrencia();

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
        return ocorrencia;
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


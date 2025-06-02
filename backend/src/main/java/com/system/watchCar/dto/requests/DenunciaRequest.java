package com.system.watchCar.dto.requests;

import com.system.watchCar.dto.LocalDTO;
import com.system.watchCar.dto.VeiculoDTO;
import com.system.watchCar.interfaces.IArtigo;

import java.util.ArrayList;
import java.util.List;

public class DenunciaRequest implements IArtigo {

    // Pessoa
    private UserRequest responsavel;
    private UserRequest denunciante;

    // Local
    private LocalRequest local;
    private LocalRequest localOcorrencia;

    // Veículo
    private List<VeiculoRequest> veiculos = new ArrayList<>();

    // Artigo
    private Long idArtigo;
    private String codArtigo;
    private String descricaoArtigo;

    public DenunciaRequest() {
    }

    public UserRequest getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(UserRequest responsavel) {
        this.responsavel = responsavel;
    }

    public UserRequest getDenunciante() {
        return denunciante;
    }

    public void setDenunciante(UserRequest denunciante) {
        this.denunciante = denunciante;
    }

    public DenunciaRequest setLocal(LocalRequest local) {
        this.local = local;
        return this;
    }

    public LocalRequest getLocal() {
        return local;
    }

    public DenunciaRequest setLocalOcorrencia(LocalRequest local) {
        this.localOcorrencia = local;
        return this;
    }

    public LocalRequest getLocalOcorrencia() {
        return localOcorrencia;
    }


    public DenunciaRequest addVeiculos(VeiculoRequest veiculo) {
        this.veiculos.add(veiculo);
        return this;
    }

    public List<VeiculoRequest> getVeiculos() {
        return veiculos;
    }


    /***********************************************
     *
     *                  Artigo
     *
     ***********************************************/
    @Override
    public DenunciaRequest setIdArtigo(Long id) {
        this.idArtigo = id;
        return this;
    }

    @Override
    public Long getIdArtigo() {
        return idArtigo;
    }

    @Override
    public DenunciaRequest setCodArtigo(String cod) {
        this.codArtigo = cod;
        return this;
    }

    @Override
    public String getCodArtigo() {
        return codArtigo;
    }

    @Override
    public DenunciaRequest setDescricaoArtigo(String descricao) {
        this.descricaoArtigo = descricao;
        return this;
    }

    @Override
    public String getDescricaoArtigo() {
        return descricaoArtigo;
    }

}


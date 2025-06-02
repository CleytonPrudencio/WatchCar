package com.system.watchCar.dto;

import com.system.watchCar.interfaces.IArtigo;
import com.system.watchCar.interfaces.IRole;
import com.system.watchCar.interfaces.IUserSimple;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class DenunciaRequest implements IUserSimple, IArtigo {

    // Pessoa
    private Long idUser;
    @NotBlank
    private String userName;
    @NotBlank
    private String email;
    @NotBlank
    private String cpf;
    private Boolean activated;
    @NotEmpty
    private List<RoleDTO> roles = new ArrayList<>();

    // Local
    private LocalDTO local;
    private LocalDTO localOcorrencia;

    // Veículo
    private List<VeiculoDTO> veiculos = new ArrayList<>();

    // Artigo
    private Long idArtigo;
    private String codArtigo;
    private String descricaoArtigo;

    @Override
    public DenunciaRequest setIdUser(Long id) {
        this.idUser = id;
        return this;
    }

    @Override
    public Long getIdUser() {
        return idUser;
    }

    @Override
    public DenunciaRequest setUserName(String username) {
        this.userName = username;
        return this;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public DenunciaRequest setPassword(String password) {
        return this;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public DenunciaRequest setCpf(String cpf) {
        this.cpf = cpf;
        return this;
    }

    @Override
    public String getCpf() {
        return cpf;
    }

    @Override
    public DenunciaRequest setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public List<RoleDTO> getRoles() {
        return roles;
    }

    @Override
    public DenunciaRequest setUserActivated(boolean active) {
        this.activated = active;
        return this;
    }

    @Override
    public Boolean getUserActivated() {
        return activated;
    }

    @Override
    public DenunciaRequest addRole(IRole role) {
        roles.add(role.toRole(RoleDTO.class));
        return this;
    }

    public DenunciaRequest setLocal(LocalDTO local) {
        this.local = local;
        return this;
    }

    public LocalDTO getLocal() {
        return local;
    }

    public DenunciaRequest setLocalOcorrencia(LocalDTO local) {
        this.localOcorrencia = local;
        return this;
    }

    public LocalDTO getLocalOcorrencia() {
        return localOcorrencia;
    }


    public DenunciaRequest addVeiculos(VeiculoDTO veiculo) {
        this.veiculos.add(veiculo);
        return this;
    }

    public List<VeiculoDTO> getVeiculos() {
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


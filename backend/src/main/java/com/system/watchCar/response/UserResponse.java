package com.system.watchCar.response;

import com.system.watchCar.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private String cpf;
    private boolean alerta;
    private String tipo;
    private String delegacia;
    private String distintivo;
    private String departamento;
    private String cargo;
    private String ra;
    private boolean ativo;

    public UserResponse toUser(User user){
        this.id = user.getId();
        this.name = user.getUsername();
        this.email = user.getEmail();
        this.cpf = user.getCpf();
        this.alerta = (user.getAlerta()) != null ? user.getAlerta() : false;
        this.tipo = user.getRole().getName().name();
        this.delegacia = user.getDelegate();
        this.distintivo = user.getBadge();
        this.departamento = user.getDepartamento();
        this.cargo = user.getCargo();
        this.ra = user.getRa();
        this.ativo = user.getAtivo();
        return this;
    }
}

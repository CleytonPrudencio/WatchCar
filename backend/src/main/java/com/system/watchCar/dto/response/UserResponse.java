package com.system.watchCar.dto.response;

import com.system.watchCar.entity.User;
import com.system.watchCar.interfaces.IResponseOK;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class UserResponse implements IResponseOK {

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
    private boolean success = false;

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public boolean getSuccess() {
        return success;
    }

    public UserResponse toUser(User user){
        this.id = user.getIdUser();
        this.name = user.getUserName();
        this.email = user.getEmail();
        this.cpf = user.getCpf();
        this.tipo = user.getRoles().stream().toList().get(1).getAuthority();
        success = Objects.nonNull(user.getCpf());
        return this;
    }
}

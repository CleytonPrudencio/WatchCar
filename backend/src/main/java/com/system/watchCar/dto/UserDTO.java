package com.system.watchCar.dto;

import com.system.watchCar.entity.Role;
import com.system.watchCar.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {
    private Long idUser;
    private String name;
    private String password;
    private String email;
    private String cpf;
    private Boolean alerta; // ALERTA pode ser true ou false
    private String delegate; // Para Policial, Agente de Segurança, Investigador
    private String badge; // Para Policial, Agente de Segurança, Investigador
    private String ra; // Para Policial, Agente de Segurança, Investigador
    private String departamento; // Para Gestor de Segurança Pública
    private String cargo; // Para Gestor de Segurança Pública
    private Boolean ativo;
    private List<RoleDTO> roles = new ArrayList<>();


    public UserDTO(User entity) {
        this.idUser = entity.getIdUser();
        this.name = entity.getUserName();
        this.password = entity.getPassword();
        this.email = entity.getEmail();
        this.cpf = entity.getCpf();

        if (entity.getRoles() != null) {
            for (Role role : entity.getRoles()) {
                roles.add(new RoleDTO(role.getIdRole(), role.getAuthority()));
            }
        }
    }
}

package com.system.watchCar.service;

import com.system.watchCar.dto.RoleDTO;
import com.system.watchCar.dto.UserDTO;
import com.system.watchCar.dto.requests.UserRequest;
import com.system.watchCar.dto.response.UserSimpleResponse;
import com.system.watchCar.entity.Role;
import com.system.watchCar.entity.User;
import com.system.watchCar.entity.UserAgente;
import com.system.watchCar.entity.UserGestor;
import com.system.watchCar.repository.RoleRepository;
import com.system.watchCar.repository.UserAgenteRepository;
import com.system.watchCar.repository.UserGestorRepository;
import com.system.watchCar.repository.UserRepository;
import com.system.watchCar.service.exceptions.UserExecption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAgenteRepository userAgenteRepository;

    @Autowired
    private UserGestorRepository userGestorRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Transactional
    public UserSimpleResponse save(UserRequest request) {

        User user = new User();
        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());
        user.setCpf(request.getCpf());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setUserActivated(true);

        // Verifica se o usuário já existe
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserExecption("User already exists with email");
        }

        // Busca e adiciona os papéis ao usuário
        for (RoleDTO roleDTO : request.getRoles()) {
            Role role = roleRepository.findById(roleDTO.getIdRole())
                    .orElseThrow(() -> new UserExecption("No role found with id: " + roleDTO.getIdRole()));
            user.addRole(role);
        }

        // Verifica se o usuário é um Agente
        UserAgente agente = null;
        if (Objects.nonNull(request.getDelegate())) {
            agente = user.toUserSimple(UserAgente.class);
            agente.setDelegate(request.getDelegate());
            agente.setBadge(request.getBadge());
            agente.setRa(request.getRa());
        }

        // Verifica se o usuário é um Gestor de Segurança
        UserGestor gestor = null;
        if (Objects.nonNull(agente) && Objects.nonNull(request.getCargo())) {
            gestor = agente.toUserSimple(UserGestor.class);
            gestor.setCargo(request.getCargo());
            gestor.setDepartment(request.getDepartment());
        }

        if (Objects.nonNull(gestor)) {
            return userGestorRepository.save(agente).toUserSimple(UserSimpleResponse.class);
        } else if (Objects.nonNull(agente)) {
            return userAgenteRepository.save(agente).toUserSimple(UserSimpleResponse.class);
        } else {
            return userRepository.save(user).toUserSimple(UserSimpleResponse.class);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username"));
    }

    @Transactional(readOnly = true)
    public UserDTO getMe() {
        User entity = authService.authenticated();
        return new UserDTO(entity);
    }
}

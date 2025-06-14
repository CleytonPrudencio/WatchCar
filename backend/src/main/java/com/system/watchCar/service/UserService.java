package com.system.watchCar.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.system.watchCar.dto.RoleDTO;
import com.system.watchCar.dto.UserDTO;
import com.system.watchCar.dto.requests.AuthDTO;
import com.system.watchCar.dto.requests.UserGestorRequest;
import com.system.watchCar.dto.response.TokenResponseDTO;
import com.system.watchCar.dto.response.UserProjection;
import com.system.watchCar.dto.response.UserResponse;
import com.system.watchCar.dto.response.UserSimpleResponse;
import com.system.watchCar.entity.User;
import com.system.watchCar.entity.UserAgente;
import com.system.watchCar.entity.UserGestor;
import com.system.watchCar.interfaces.IGestorSecurity;
import com.system.watchCar.interfaces.IUserSimple;
import com.system.watchCar.repository.RoleRepository;
import com.system.watchCar.repository.UserAgenteRepository;
import com.system.watchCar.repository.UserGestorRepository;
import com.system.watchCar.repository.UserRepository;
import com.system.watchCar.service.exceptions.UserExecption;
import com.system.watchCar.service.interfaces.IAuthService;
import com.system.watchCar.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;

@Service
public class UserService implements IAuthService {

    @Value("${security.client-id}")
    private String clientId;

    @Value("${security.client-secret}")
    private String clientSecret;

    @Value("${security.jwt.duration}")
    private Integer jwtDurationSeconds;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAgenteRepository userAgenteRepository;

    @Autowired
    private UserGestorRepository userGestorRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    @Transactional
    public UserSimpleResponse save(UserGestorRequest request) {

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
        for (RoleDTO role : request.getRoles()) {
            user.addRole(roleService.findById(role.getIdRole()));
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
            gestor = agente.toAgente(UserGestor.class);
            gestor.setCargo(request.getCargo());
            gestor.setDepartment(request.getDepartment());
        }

        UserExecption.validationWithPassword(user);
        if (Objects.nonNull(gestor)) {
            return userGestorRepository.save(gestor).toUserSimple(UserSimpleResponse.class);
        } else if (Objects.nonNull(agente)) {
            return userAgenteRepository.save(agente).toUserSimple(UserSimpleResponse.class);
        } else {
            return save(user).toUserSimple(UserSimpleResponse.class);
        }
    }

    @Transactional
    public IUserSimple save(IUserSimple entity) {
        return userRepository.save(entity.toUserSimple(User.class));
    }

    @Transactional(readOnly = true)
    public IGestorSecurity findById(Long id) {
        if (id == null) {
            throw new UserExecption("User ID cannot be null");
        }
        List<UserProjection> list = userRepository.searchById(id);
        Msg.System("user: "+ list.get(0).print(), getClass());

        return list.get(0).toGestor();
    }

    @Transactional(readOnly = true)
    public UserDTO findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserDTO::new)
                .orElseThrow(() -> new UserExecption("User not found with email: " + email));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByCpf(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username"));
    }

    @Override
    public TokenResponseDTO obterToken(AuthDTO authDto) {
        User user = userRepository.findByCpf(authDto.cpf())
                .orElseThrow(() -> new UserExecption("Usuário não encontrado com o CPF: " + authDto.cpf()));
        return TokenResponseDTO
                .builder()
                .access_token(geraTokenJwt(user))
                .expire(Instant.now().plusSeconds(jwtDurationSeconds))
                .refreshToken(geraTokenJwt(user))
                .build();
    }

    @Override
    public String validaTokenJwt(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(clientSecret);
            return JWT.require(algorithm).
                    withIssuer(clientId)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new UserExecption("Token inválido ou expirado: " + e.getMessage());
        }
    }

    @Override
    public TokenResponseDTO obterRefreshToken(String refreshToken) {
        String login = validaTokenJwt(refreshToken);
        User usuario = userRepository.findByCpf(login).orElseThrow(() -> new UserExecption("Usuário não encontrado com o CPF: " + login));

        var autentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(autentication);

        return TokenResponseDTO
                .builder()
                .access_token(geraTokenJwt(usuario))
                .expire(genExpirationDateTime())
                .refreshToken(geraTokenJwt(usuario))
                .build();
    }

    public String geraTokenJwt(User usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(clientSecret);
            return JWT.create()
                    .withIssuer(clientId)
                    .withSubject(usuario.getUsername())
                    .withClaim("id", usuario.getIdUser())
                    .withClaim("name", usuario.getUserName())
                    .withClaim("roles", usuario.getRoles().stream().map(role -> role.getAuthority()).toList())
                    .withExpiresAt(genExpirationDateTime())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new UserExecption("Erro ao tentar gerar o token! " + exception.getMessage());
        }
    }

    private Instant genExpirationDateTime() {
        return LocalDateTime.now().plusSeconds(jwtDurationSeconds).toInstant(ZoneOffset.of("-03:00"));
    }
}

package com.system.watchCar.config;

import com.system.watchCar.entity.User;
import com.system.watchCar.repository.UserRepository;
import com.system.watchCar.service.interfaces.IAuthService;
import com.system.watchCar.service.exceptions.UserExecption;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private IAuthService autenticacaoService;

    @Autowired
    private UserRepository usuarioRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = extraiTokenHeader(request);

        if (token != null) {
            String cpf = autenticacaoService.validaTokenJwt(token);
            User usuario = usuarioRepository.findByCpf(cpf).orElseThrow(()-> new UserExecption("Usuário não encontrado"));

            var autentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(autentication);
        }

        filterChain.doFilter(request, response);
    }

    public String extraiTokenHeader(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if(Objects.isNull(authHeader)
                || authHeader.isBlank()
                || !authHeader.contains("Bearer")
                || authHeader.split(" ").length < 2) {
            return null;
        }
        var token = authHeader.split(" ")[1];
        if (token.isBlank()) {
            return null;
        }
        return token;
    }
}

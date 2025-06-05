package com.system.watchCar.controller;

import com.system.watchCar.dto.requests.AuthDTO;
import com.system.watchCar.dto.response.TokenResponseDTO;
import com.system.watchCar.service.interfaces.IAuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IAuthService authService;

    @PostMapping(value = "/login")
    public ResponseEntity<TokenResponseDTO> login(@Valid @RequestBody AuthDTO dto) {
        var authManager = new UsernamePasswordAuthenticationToken(dto.cpf(), dto.password());
        authenticationManager.authenticate(authManager);
        return ResponseEntity.ok(authService.obterToken(dto));
    }


}

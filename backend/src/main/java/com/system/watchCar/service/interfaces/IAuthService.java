package com.system.watchCar.service.interfaces;

import com.system.watchCar.dto.requests.AuthDTO;
import com.system.watchCar.dto.response.TokenResponseDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IAuthService extends UserDetailsService {

    public TokenResponseDTO obterToken(AuthDTO authDto);
    public String validaTokenJwt(String token);

    TokenResponseDTO obterRefreshToken(String s);
}

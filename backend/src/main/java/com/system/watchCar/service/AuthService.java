package com.system.watchCar.service;

import com.system.watchCar.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserService userService;

    public void validateSelfOrAdmin(long userId) {
        User me = userService.authenticated();
        if (!me.hasRole("ROLE_ADMIN") && !me.getIdUser().equals(userId)) {
            throw new RuntimeException("Access denied");
        }
    }


}

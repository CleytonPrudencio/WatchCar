package com.system.watchCar.controller;

import com.system.watchCar.dto.RoleDTO;
import com.system.watchCar.dto.UserDTO;
import com.system.watchCar.dto.requests.UserRequest;
import com.system.watchCar.dto.response.UserSimpleResponse;
import com.system.watchCar.entity.UserAgente;
import com.system.watchCar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping(value = "/register")
    public ResponseEntity<UserSimpleResponse> register(@RequestBody UserRequest request) {
        UserSimpleResponse createdUser = service.save(request);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping(value = "/me")
    public ResponseEntity<UserDTO> getMe() {
        UserDTO dto = service.getMe();
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<UserDTO> hello() {
        UserDTO dto = new UserDTO();
        dto.setUserName("Elder Barbosa");
        dto.setEmail("elder@gmail.com");
        dto.setCpf("12345678901");
        dto.setPassword("123456");
        RoleDTO roleDTO = new RoleDTO(1L, "ROLE_ADMIN");
        dto.addRole(roleDTO);
        return ResponseEntity.ok(dto);
    }
}

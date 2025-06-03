package com.system.watchCar.controller;

import com.system.watchCar.controller.openapi.UserOpenApi;
import com.system.watchCar.dto.RoleDTO;
import com.system.watchCar.dto.UserDTO;
import com.system.watchCar.dto.requests.UserGestorRequest;
import com.system.watchCar.dto.response.UserSimpleResponse;
import com.system.watchCar.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController implements UserOpenApi {

    @Autowired
    private UserService service;

    @PostMapping(value = "/register")
    public ResponseEntity<UserSimpleResponse> register(@Valid @RequestBody UserGestorRequest request) {
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

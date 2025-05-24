package com.system.watchCar.controller;

import com.system.watchCar.dto.response.LocalResponse;
import com.system.watchCar.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cep")
public class ViaCepController {

    @Autowired
    private ViaCepService service;

    @GetMapping
    public LocalResponse getLocalByCep(@RequestBody String cep) {
        return service.findByCep(cep);
    }

}

package com.system.watchCar.controller;

import com.system.watchCar.config.SwaggerConfig;
import com.system.watchCar.dto.response.LocalResponse;
import com.system.watchCar.service.ViaCepService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cep")
@Tag(name = "ViaCepController", description = "Controller para consulta de CEP via ViaCEP API")
public class ViaCepController {

    @Autowired
    private ViaCepService service;

    @Operation(summary = "Consulta CEP", description = "Consulta o CEP informado e retorna os dados de endereço correspondentes.")
    @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso")
    @GetMapping
    public LocalResponse getLocalByCep(@RequestBody String cep) {
        return service.findByCep(cep);
    }

}

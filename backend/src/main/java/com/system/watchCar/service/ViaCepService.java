package com.system.watchCar.service;

import com.system.watchCar.response.LocalResponse;
import com.system.watchCar.service.exceptions.LocalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class ViaCepService {

    private final String VIACEP_URL = "https://viacep.com.br/ws/{cep}/json/";

    @Autowired
    private RestTemplate restTemplate;

    public LocalResponse findByCep(String cep) {
        if (Objects.isNull(cep) || cep.isBlank()) {
            throw new LocalException("CEP campo requerido não preenchido");
        }
        String numberCep = cep.replaceAll("[^0-9]", "");
        if (numberCep.length() != 8) {
            throw new LocalException("CEP inválido");
        }
        LocalResponse response;
        try {
            response = restTemplate.getForObject(VIACEP_URL, LocalResponse.class, numberCep);
        } catch (Exception e) {
            throw new LocalException("CEP inválido ou não encontrado " + numberCep);
        }
        LocalException.validation(response);// Validação do retorno
        return response;
    }
}

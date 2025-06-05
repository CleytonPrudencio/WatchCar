package com.system.watchCar.service.exceptions;

import com.system.watchCar.interfaces.ILocal;

import java.util.Objects;

public class LocalException extends RuntimeException {
    private String msg;

    public LocalException(String message) {
        super(message);
        msg = message;
    }

    public LocalException(String message, Exception exception) {
        super(message);
        msg = message+ " - Erro: " + exception.getMessage();
    }

    public static boolean validation(ILocal local) {
        if (Objects.isNull(local)) {
            throw new LocalException("Local não pode ser nulo");
        }
        if (Objects.isNull(local.getCep()) || local.getCep().isEmpty()) {
            throw new LocalException("CEP inválido ou não existe");
        }
        if (Objects.isNull(local.getLogradouro()) || local.getLogradouro().isEmpty()) {
            throw new LocalException("Logradouro obrigatório requerido");
        }
        if (Objects.isNull(local.getBairro()) || local.getBairro().isEmpty()) {
            throw new LocalException("Bairro obrigatório requerido");
        }
        if (Objects.isNull(local.getCidade()) || local.getCidade().isEmpty()) {
            throw new LocalException("Cidade obrigatório requerido");
        }
        if (Objects.isNull(local.getEstado()) || local.getEstado().isEmpty()) {
            throw new LocalException("Estado obrigatório requerido");
        }
        return true;
    }

    @Override
    public String getMessage() {
        return msg;
    }
}

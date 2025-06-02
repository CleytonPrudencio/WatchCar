package com.system.watchCar.service.exceptions;

import com.system.watchCar.dto.exceptions.FieldDTO;
import com.system.watchCar.interfaces.IFieldNameError;
import com.system.watchCar.interfaces.IUserSimple;

import java.util.Objects;

public class UserExecption extends RuntimeException implements IFieldNameError {

    private String msg;
    private FieldDTO field;

    public UserExecption(String message) {
        super(message);
        msg = message;
    }

    public UserExecption(String message, Exception exception) {
        msg = message + " e " + exception.getMessage();
    }

    public UserExecption(String fieldName, String message) {
        field = new FieldDTO(fieldName, message);
    }

    public static boolean validation(String... args) {
        for (String arg : args) {
            if (Objects.isNull(arg) || arg.isBlank()) {
                throw new UserExecption("O campo " + args + " obrigatório não preenchido");
            }
        }
        return true;
    }

    public static boolean validation(IUserSimple user) {
        if(Objects.isNull(user)) {
            throw new UserExecption("Usuário não informado");
        }
        if(Objects.isNull(user.getUserName()) || user.getUserName().isBlank()) {
            throw new UserExecption("O campo nome do usuário é obrigatório");
        }
        if(Objects.isNull(user.getEmail()) || user.getEmail().isBlank()) {
            throw new UserExecption("O campo email do usuário é obrigatório");
        }
        if(Objects.isNull(user.getCpf()) || user.getCpf().isBlank()) {
            throw new UserExecption("O campo CPF do usuário é obrigatório");
        }
        return true;
    }

    public static boolean validationWithPassword(IUserSimple user) {
        validation(user);
        if(Objects.isNull(user.getPassword()) || user.getPassword().isBlank()) {
            throw new UserExecption("O campo senha do usuário é obrigatório");
        }
        return true;
    }

    @Override
    public FieldDTO getField() {
        return field;
    }

    @Override
    public String getMessage() {
        if (Objects.nonNull(field)) {
            return toField();
        }
        return msg;
    }
}

package com.system.watchCar.service.exceptions;

import com.system.watchCar.dto.exceptions.FieldDTO;
import com.system.watchCar.interfaces.IFieldNameError;

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

package com.system.watchCar.dto.exceptions;

import com.system.watchCar.interfaces.IFieldNameError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ErrorsDTO extends ErrorDTO {

    private List<FieldDTO> errors = new ArrayList<>();

    public ErrorsDTO(HttpStatus status, Exception exception, HttpServletRequest request) {
        super(status, exception, request);
        if(exception instanceof IFieldNameError error){
            if (Objects.nonNull(error.getField())) {
                errors.add(error.getField());
            }
        }
    }

    public ErrorsDTO(HttpStatus status, MethodArgumentNotValidException validation, HttpServletRequest request) {
        super(status, validation, request);
        for (var error : validation.getFieldErrors()) {
            errors.add(new FieldDTO(error.getField(), error.getDefaultMessage()));
        }
    }

    public List<FieldDTO> getErrors() {
        return errors;
    }
}

package com.system.watchCar.dto.exceptions;

import com.system.watchCar.service.exceptions.UserExecption;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ErrorDTO {
    private final Instant timestamp = Instant.now();
    private int status;
    private String messege;
    private String path;
    private String method;
    private List<FieldDTO> errors;

    public ErrorDTO(HttpStatus status, Exception exception, HttpServletRequest request) {
        this.status = status.value();
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.messege = exception.getMessage();
        errors = new ArrayList<>();
        if(exception instanceof UserExecption userExecption) {
            errors.add(userExecption.getField());
        }else {
            errors.add(new FieldDTO(status.name(), exception.getMessage()));
        }
    }

    public ErrorDTO(HttpStatus status, MethodArgumentNotValidException validation, HttpServletRequest request) {
        this.status = status.value();
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.messege = validation.getMessage();
        errors = new ArrayList<>();
        for (FieldError fields : validation.getBindingResult().getFieldErrors()) {
            errors.add(new FieldDTO(fields.getField(), fields.getDefaultMessage()));
        }
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getMessege() {
        return messege;
    }

    public String getPath() {
        return path;
    }

    public String getMethod() {
        return method;
    }

    public List<FieldDTO> getErrors() {
        return errors;
    }
}

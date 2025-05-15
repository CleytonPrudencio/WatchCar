package com.system.watchCar.dto.exceptions;

import com.system.watchCar.interfaces.IFieldNameError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class ErrorResponseDTO extends ErrorsDTO {

    private boolean isErrorField = false;
    private HttpStatus status;
    private Exception exception;
    private HttpServletRequest request;

    public ErrorResponseDTO(HttpStatus status, Exception exception, HttpServletRequest request) {
        super(status, exception, request);
        this.status = status;
        this.exception = exception;
        this.request = request;
        if (exception instanceof IFieldNameError error) {
            if (Objects.nonNull(error.getField())) {
                isErrorField = true;
            } else {
                isErrorField = false;
            }
        }
    }

    public ErrorResponseDTO(HttpStatus status, MethodArgumentNotValidException validation, HttpServletRequest request) {
        super(status, validation, request);
        this.status = status;
        this.exception = validation;
        this.request = request;
    }

    public ErrorDTO response() {
        if (isErrorField) {
            return new ErrorsDTO(status, exception, request);
        }
        return new ErrorDTO(status, exception, request);
    }
}

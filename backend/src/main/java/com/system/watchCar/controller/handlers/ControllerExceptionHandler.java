package com.system.watchCar.controller.handlers;

import com.system.watchCar.dto.exceptions.ErrorDTO;
import com.system.watchCar.dto.exceptions.ErrorResponseDTO;
import com.system.watchCar.dto.exceptions.ErrorsDTO;
import com.system.watchCar.response.LocalResponse;
import com.system.watchCar.service.exceptions.LocalException;
import com.system.watchCar.service.exceptions.UserExecption;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> methodArgumentNotValidException(MethodArgumentNotValidException error, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        return ResponseEntity.status(status).body(new ErrorsDTO(status, error, request));
    }

    @ExceptionHandler(UserExecption.class)
    public ResponseEntity<ErrorDTO> userException(UserExecption error, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        return ResponseEntity.status(status).body(new ErrorResponseDTO(status, error, request).response());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> illegalArgumentException(IllegalArgumentException error, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(new ErrorDTO(status, error, request));
    }

    @ExceptionHandler(LocalException.class)
    public ResponseEntity<ErrorDTO> localException(LocalException error, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(new ErrorDTO(status, error, request));
    }

}

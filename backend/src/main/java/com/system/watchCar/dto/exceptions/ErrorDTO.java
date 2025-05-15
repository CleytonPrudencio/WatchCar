package com.system.watchCar.dto.exceptions;

import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

public class NameErrorDTO {
    private final Instant timestamp = Instant.now();
    protected int status;
    protected String messege;
    protected String path;
    protected String method;

    public NameErrorDTO(HttpStatus status, Exception exception, HttpServletRequest request) {
        this.status = status.value();
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.messege = exception.getMessage();
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
}

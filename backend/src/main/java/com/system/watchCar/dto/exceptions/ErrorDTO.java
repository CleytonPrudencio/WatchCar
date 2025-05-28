package com.system.watchCar.dto.exceptions;

import com.system.watchCar.interfaces.IResponseOK;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

import java.time.Instant;

public class ErrorDTO implements IResponseOK {
    private final Instant timestamp = Instant.now();
    protected int status;
    protected String messege;
    protected String path;
    protected String method;
    protected boolean success = false;

    public ErrorDTO(HttpStatus status, Exception exception, HttpServletRequest request) {
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

    @Override
    public boolean getSuccess() {
        return success;
    }
}

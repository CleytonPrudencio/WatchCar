package com.system.watchCar.dto.exceptions;

import com.system.watchCar.interfaces.IResponseOK;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

import java.sql.SQLException;
import java.time.Instant;
import java.util.Objects;

public class ErrorDTO implements IResponseOK {
    private final Instant timestamp = Instant.now();
    protected int status;
    protected String message;
    protected String path;
    protected String method;

    @Schema(description = "Indicates whether the operation was successful or not", example = "false")
    protected boolean success = false;

    public ErrorDTO(HttpStatus status, Exception exception, HttpServletRequest request) {
        this.status = status.value();
        this.path = request.getRequestURI();
        this.method = request.getMethod();

        Throwable cause = exception.getCause();
        while (cause != null) {
            if (cause instanceof SQLException sqlEx) {
                message = sqlEx.getMessage();
                break;
            }
        }
        if (Objects.isNull(message) || message.isBlank()) {
            message = exception.getMessage();
        }
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
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

package com.system.watchCar.dto.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FieldDTO {
    private Object fieldName;
    private String message;

    @Override
    public String toString() {
        return String.format("%s: %s", fieldName, message);
    }
}

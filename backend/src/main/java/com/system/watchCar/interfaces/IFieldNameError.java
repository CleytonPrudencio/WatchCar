package com.system.watchCar.interfaces;

import com.system.watchCar.dto.exceptions.FieldDTO;

public interface IFieldNameError {

    FieldDTO getField();

    default String toField(){
        return String.format("%s: %s", getField().getFieldName(), getField().getMessage());
    }
}

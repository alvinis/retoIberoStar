package com.jencys.iberostar.app.exception;

import lombok.Data;

public class MissingParameterException extends RuntimeException{
    public MissingParameterException(String parameterName) {
        super("El parámetro '" + parameterName + "' es obligatorio.");
    }
}

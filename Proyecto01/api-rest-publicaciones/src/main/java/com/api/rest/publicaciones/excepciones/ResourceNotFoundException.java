package com.api.rest.publicaciones.excepciones;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException() {

    }

    public ResourceNotFoundException(String mensaje) {
        super(mensaje);
    }

    public ResourceNotFoundException(String mensaje, Throwable error) {
        super(mensaje, error);
    }
}
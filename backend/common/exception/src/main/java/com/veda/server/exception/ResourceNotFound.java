package com.veda.server.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFound extends AppException {

    public ResourceNotFound(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}

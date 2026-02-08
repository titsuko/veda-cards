package com.veda.server.exception;

import org.springframework.http.HttpStatus;

public class CardNotFound extends AppException {
    public CardNotFound(Integer id) {
        super("Card not found with id: " + id, HttpStatus.NOT_FOUND);
    }
}
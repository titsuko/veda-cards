package com.veda.server.exception;

import org.springframework.http.HttpStatus;

public class CategoryNotFound extends AppException {
    public CategoryNotFound(Integer id) {
        super("Category not found with id: " + id, HttpStatus.NOT_FOUND);
    }
}
package com.veda.server.exception;

import org.springframework.http.HttpStatus;

public class DuplicateCategorySlug extends AppException {
    public DuplicateCategorySlug(String slug) {
        super("Category with slug '" + slug + "' already exists", HttpStatus.CONFLICT);
    }
}
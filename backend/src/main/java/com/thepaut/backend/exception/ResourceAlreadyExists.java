package com.thepaut.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ResourceAlreadyExists extends RuntimeException {

    public ResourceAlreadyExists(String entityType, String identifier) {
        super("The " + entityType + " " + identifier + " already exist !" );
    }
}
package com.thepaut.backend.exception;

import lombok.Getter;

@Getter
public class APIException  extends RuntimeException {

    protected final Long id;

    protected final String path;

    protected final String code;

    protected final String entityType;

    public APIException(Long id, String entityType, String path, String code, String message) {
        super(message);
        this.entityType = entityType;
        this.id = id;
        this.path = path;
        this.code = code;
    }


}

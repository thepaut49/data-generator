package com.thepaut.backend.model.data;

import lombok.Getter;


@Getter
public enum FieldType {
    DATE,
    DATETIME,
    TIME,
    DECIMAL_NUMBER,
    INTEGER,
    BOOLEAN,
    STRING,
    CHAR,
    SINGLE_ASSOCIATION,
    LIST_ASSOCIATION;
}

package com.thepaut.backend.utils;

public class Constants {

    /*** EXCEPTION CODES ***/
    public static final String CODE_SAMPLE_DATA_CATEGORY_NOT_FOUND = "SAMPLE_DATA_CATEGORY_NOT_FOUND";

    public static final String CODE_SAMPLE_DATA_NOT_FOUND = "SAMPLE_DATA_NOT_FOUND";

    public static final String CODE_SAMPLE_DATA_CATEGORY_VERSION_NOT_FOUND = "SAMPLE_DATA_CATEGORY_VERSION_NOT_FOUND";

    public static final String CODE_SAMPLE_DATA_VERSION_NOT_FOUND = "SAMPLE_DATA_VERSION_NOT_FOUND";

    /*** EXCEPTION MESSAGES ***/

    public static final String MESSAGE_RESOURCE_NOT_FOUND = "%s %s inconnu !";

    public static final String MESSAGE_VERSION_RESOURCE_NOT_FOUND = "The version %s of entity %s avec l'id %s est inconnu !";

    public static final String MESSAGE_RESOURCE_ALREADY_EXIST = "The %s with id %s already exist !";


    /*** Paths ***/

    public static final String SLASH = "/";

    public static final String BASE_PATH_SAMPLE_DATA = "api/sample-datas";

    public static final String BASE_PATH_SAMPLE_DATA_CATEGORY = "api/sample-data-categories";


    private Constants() {
        throw new IllegalStateException("Utility class");
    }


}

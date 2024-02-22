package com.thepaut.backend.service;

import com.thepaut.backend.model.data.Field;

import java.util.List;

public interface IFieldService {

    /**
     * get all last version of fields corresponding to criteria
     * @param apiVersion
     * @param fieldName
     * @param fieldType
     * @return
     */
    List<Field> getFields(String apiVersion, String fieldName, String fieldType);

    /**
     * get all fields corresponding to criteria by default we return field with last version
     * @param fieldId is required
     * @param fieldVersion is optional
     * @param apiVersion
     * @param fieldName
     * @param fieldType
     * @return
     */
    Field getField(Long fieldId, long fieldVersion, String apiVersion, String fieldName, String fieldType);

    /**
     * delete of last version of the field
     * @param fieldId
     * @return
     */
    Field rollbackFieldUpdate(Long fieldId);

    Field createNewField();

    Field updateField(Long fieldId);

    boolean deleteField(Long fieldId);

    boolean deleteFields(Long modelId, List<Long> fieldIds);
}

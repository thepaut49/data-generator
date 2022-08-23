package com.thepaut.backend.service;

import com.thepaut.backend.model.data.Field;

import java.util.List;

public class FieldService implements IFieldService {

    public List<Field> getFields(String apiVersion, String fieldName, String fieldType) {
        return null;
    }

    @Override
    public Field getField(Long fieldId, long fieldVersion, String apiVersion, String fieldName, String fieldType) {
        return null;
    }

    @Override
    public Field rollbackFieldUpdate(Long fieldId) {
        return null;
    }

    @Override
    public Field createNewField() {
        return null;
    }

    @Override
    public Field updateField(Long fieldId) {
        return null;
    }

    @Override
    public boolean deleteField(Long fieldId) {
        return false;
    }

    @Override
    public boolean deleteFields(Long modelId, List<Long> fieldIds) {
        return false;
    }


}

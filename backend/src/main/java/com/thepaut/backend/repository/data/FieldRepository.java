package com.thepaut.backend.repository.data;

import com.thepaut.backend.model.data.Field;
import com.thepaut.backend.model.data.FieldType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FieldRepository extends JpaRepository<Field, Long> {

   /* @Query("SELECT f FROM Field f WHERE (:fieldName is null or f.name like %:fieldName%) and (:apiVersion is null"
            + " or f.apiVersion like %:apiVersion%) and (:fieldType is null or f.fieldType like %:fieldType%)" +
            "and (:modelId is null or f.model.entityId like %:modelId%)")
    List<Field> getFields(Long modelId, String apiVersion, String fieldName, String fieldType);*/

    List<Field> findByApiVersionAndFieldNameAndFieldType( String apiVersion, String fieldName, FieldType fieldType);

    List<Field> findByFieldNameAndFieldType( String fieldName, FieldType fieldType);

    /*Field getField(Long fieldId, long fieldVersion, String apiVersion, String fieldName, String fieldType);

    Field rollbackFieldUpdate(Long fieldId);

    Field createNewField();

    Field updateField(Long fieldId);

    boolean deleteField(Long fieldId);

    boolean deleteFields(Long modelId, List<Long> fieldIds);*/
}
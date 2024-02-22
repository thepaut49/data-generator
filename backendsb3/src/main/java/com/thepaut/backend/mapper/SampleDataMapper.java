package com.thepaut.backend.mapper;

import com.thepaut.backend.dto.SampleDataCreationDto;
import com.thepaut.backend.dto.SampleDataDto;
import com.thepaut.backend.dto.audit.SampleDataAuditDto;
import com.thepaut.backend.model.data.SampleData;
import com.thepaut.backend.model.data.audit.SampleDataAudit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper( uses = { SampleDataCategoryMapper.class})
public interface  SampleDataMapper {
    SampleDataMapper INSTANCE = Mappers.getMapper( SampleDataMapper.class );

    @Mapping(target="versions", source="versions")
    @Mapping(target = "category.id", source="categoryId")
    @Mapping(target = "category.name", source="categoryName")
    @Mapping(target = "blobTypeValue", source="blobTypeValue")
    SampleData convertUpdateDtoToEntity(SampleDataDto updateDto);

    @Mapping(target="versions", source="versions")
    @Mapping(target = "categoryId", source="category.id")
    @Mapping(target = "categoryName", source="category.name")
    @Mapping(target = "blobTypeValue", source="blobTypeValue")
    SampleDataDto convertEntityToUpdateDto(SampleData entity);

    @Mapping(target="modifiedAt", expression="java(java.time.LocalDateTime.now())")
    @Mapping(target = "category.id", source="categoryId")
    @Mapping(target = "blobTypeValue", source="isBlobTypeValue")
    SampleData convertCreationDtoToEntity(SampleDataCreationDto creationDto);

    @Mapping(target = "category.id", source="categoryId")
    @Mapping(target = "blobTypeValue", source="blobTypeValue")
    SampleData convertAuditToEntity(SampleDataAudit audit);

    @Mapping(target = "categoryId", source="category.id")
    @Mapping(target = "blobTypeValue", source="blobTypeValue")
    SampleDataAudit convertEntityToAudit(SampleData entity);

    @Mapping(target = "blobTypeValue", source="blobTypeValue")
    SampleDataAudit convertUpdateDtoToEntity(SampleDataAuditDto updateDto);

    @Mapping(target = "blobTypeValue", source="blobTypeValue")
    SampleDataAuditDto convertEntityToUpdateDto(SampleDataAudit audit);

}
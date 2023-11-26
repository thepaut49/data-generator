package com.thepaut.backend.mapper;

import com.thepaut.backend.dto.SampleDataCreationDto;
import com.thepaut.backend.dto.SampleDataDto;
import com.thepaut.backend.mapper.audit.SampleDataAuditMapper;
import com.thepaut.backend.model.data.SampleData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper( uses = { SampleDataCategoryMapper.class, SampleDataAuditMapper.class})
public interface  SampleDataMapper {
    SampleDataMapper INSTANCE = Mappers.getMapper( SampleDataMapper.class );

    @Mapping(target="versions", source="versions")
    SampleData convertUpdateDtoToEntity(SampleDataDto updateDto);

    @Mapping(target="versions", source="versions")
    SampleDataDto convertEntityToUpdateDto(SampleData entity);

    @Mapping(target="modifiedAt", expression="java(java.time.LocalDateTime.now())")
    SampleData convertCreationDtoToEntity(SampleDataCreationDto creationDto);

    SampleDataCreationDto convertEntityToCreationDto(SampleData entity);
}
package com.thepaut.backend.mapper;

import com.thepaut.backend.dto.SampleDataCategoryCreationDto;
import com.thepaut.backend.dto.SampleDataCategoryDto;
import com.thepaut.backend.dto.audit.SampleDataCategoryAuditDto;
import com.thepaut.backend.model.data.SampleDataCategory;
import com.thepaut.backend.model.data.audit.SampleDataCategoryAudit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper()
public interface  SampleDataCategoryMapper {

    SampleDataCategoryMapper INSTANCE = Mappers.getMapper( SampleDataCategoryMapper.class );

    @Mapping(target="versions", source="versions")
    SampleDataCategory convertUpdateDtoToEntity(SampleDataCategoryDto updateDto);

   @Mapping(target="versions", source="versions")
    SampleDataCategoryDto convertEntityToUpdateDto(SampleDataCategory entity);

    @Mapping(target="modifiedAt", expression="java(java.time.LocalDateTime.now())")
    SampleDataCategory convertCreationDtoToEntity(SampleDataCategoryCreationDto creationDto);

    SampleDataCategory convertAuditToEntity(SampleDataCategoryAudit audit);

    SampleDataCategoryAudit convertEntityToAudit(SampleDataCategory entity);

    SampleDataCategoryAudit convertUpdateDtoToEntity(SampleDataCategoryAuditDto updateDto);

    SampleDataCategoryAuditDto convertEntityToUpdateDto(SampleDataCategoryAudit audit);

}
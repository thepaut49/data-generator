package com.thepaut.backend.mapper;

import com.thepaut.backend.dto.SampleDataCategoryCreationDto;
import com.thepaut.backend.dto.SampleDataCategoryDto;
import com.thepaut.backend.model.data.SampleDataCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface  SampleDataCategoryMapper {

    SampleDataCategoryMapper INSTANCE = Mappers.getMapper( SampleDataCategoryMapper.class );

    SampleDataCategory convert(SampleDataCategoryDto sampleDataCategoryDto);

    SampleDataCategoryDto convert(SampleDataCategory sampleDataCategory);

    @Mapping(target="modifiedAt", expression="java(java.time.LocalDateTime.now())")
    SampleDataCategory convertFromCreationDto(SampleDataCategoryCreationDto sampleDataCategoryCreationDto);

    SampleDataCategoryCreationDto convertToCreationDto(SampleDataCategory sampleDataCategory);
    
}
package com.thepaut.backend.mapper;

import com.thepaut.backend.dto.SampleDataCategoryDto;
import com.thepaut.backend.model.data.SampleDataCategory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface  SampleDataCategoryMapper {

    SampleDataCategoryMapper INSTANCE = Mappers.getMapper( SampleDataCategoryMapper.class );

    SampleDataCategory convert(SampleDataCategoryDto sampleDataCategoryDto);

    SampleDataCategoryDto convert(SampleDataCategory sampleDataCategory);
    
}
package com.thepaut.backend.mapper;

import com.thepaut.backend.dto.SampleDataDto;
import com.thepaut.backend.model.data.SampleData;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface  SampleDataMapper {

    SampleDataMapper INSTANCE = Mappers.getMapper( SampleDataMapper.class );

    SampleData convert(SampleDataDto sampleDataDto);

    SampleDataDto convert(SampleData sampleData);
    
}
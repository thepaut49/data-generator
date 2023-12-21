package com.thepaut.backend.mapper.audit;

import com.thepaut.backend.dto.audit.SampleDataAuditDto;
import com.thepaut.backend.mapper.SampleDataCategoryMapper;
import com.thepaut.backend.model.data.SampleData;
import com.thepaut.backend.model.data.audit.SampleDataAudit;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper( uses = { SampleDataCategoryMapper.class})
public interface SampleDataAuditMapper {
    SampleDataAuditMapper INSTANCE = Mappers.getMapper( SampleDataAuditMapper.class );

    SampleData convertAuditToEntity(SampleDataAudit audit);

    SampleDataAudit convertEntityToAudit(SampleData entity);

    SampleDataAudit convertUpdateDtoToEntity(SampleDataAuditDto updateDto);

    SampleDataAuditDto convertEntityToUpdateDto(SampleDataAudit audit);

}
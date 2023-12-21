package com.thepaut.backend.dto;

import com.thepaut.backend.dto.audit.SampleDataCategoryAuditDto;
import lombok.Data;

@Data
public class SampleDataCategoryDto extends GenericDto<SampleDataCategoryAuditDto> {
    private String name;
}

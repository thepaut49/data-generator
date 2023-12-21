package com.thepaut.backend.dto.audit;

import lombok.Data;

@Data
public class SampleDataCategoryAuditDto extends GenericAuditDto {
    private String name;
}

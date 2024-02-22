package com.thepaut.backend.dto;

import com.thepaut.backend.dto.audit.SampleDataAuditDto;
import lombok.Data;

@Data
public class SampleDataDto  extends GenericDto<SampleDataAuditDto> {
    private Long categoryId;
    private String categoryName;
    private String key;
    private String value;
    private boolean isBlobTypeValue;
    private String blobValue;
}


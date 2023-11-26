package com.thepaut.backend.dto.audit;

import lombok.Data;

@Data
public class SampleDataAuditDto extends GenericAuditDto {
    private String categoryName;
    private String key;
    private String value;
    private boolean isBlobValue;
    private String blobValue;
}

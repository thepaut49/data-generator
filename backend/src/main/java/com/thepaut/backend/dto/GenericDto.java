package com.thepaut.backend.dto;

import com.thepaut.backend.dto.audit.GenericAuditDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public abstract class GenericDto<AuditDto extends GenericAuditDto> {
    protected Long id;
    protected Long version;
    protected String modifiedBy;
    protected LocalDateTime modifiedAt;
    protected List<AuditDto> versions;

}

package com.thepaut.backend.dto.audit;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class GenericAuditDto {
    protected Long id;
    protected Long version;
    protected String modifiedBy;
    protected LocalDateTime modifiedAt;
}

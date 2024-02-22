package com.thepaut.backend.repository.data.audit;

import com.thepaut.backend.model.data.audit.SampleDataAudit;
import org.springframework.stereotype.Repository;


@Repository
public interface SampleDataAuditRepository extends GenericAuditRepository<SampleDataAudit> {
}
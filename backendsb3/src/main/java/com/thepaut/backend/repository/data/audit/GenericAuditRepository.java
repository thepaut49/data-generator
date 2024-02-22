package com.thepaut.backend.repository.data.audit;

import com.thepaut.backend.model.data.audit.EntityAuditId;
import com.thepaut.backend.model.data.audit.GenericEntityAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface GenericAuditRepository<T extends GenericEntityAudit> extends JpaRepository<T, EntityAuditId> {
    List<T> findByIdOrderByVersionDesc(Long id);
    List<T> findByIdAndVersionLessThanEqualOrderByVersionDesc(Long id, Long version);
    Optional<T> findFirstByIdOrderByVersionDesc(Long id);
    Optional<T> findFirstByIdAndVersion(Long id, Long version);
    Long deleteByIdAndVersion(Long id, Long version);
    Long deleteFirstByIdOrderByVersionDesc(Long id);
    Long deleteByIdAndVersionGreaterThanEqual(Long id, Long version);
    Long deleteById(Long id);
}
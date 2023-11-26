package com.thepaut.backend.model.data.audit;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@IdClass(EntityAuditId.class)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class GenericEntityAudit implements Serializable {

    @Id
    protected long id;

    @Id
    protected long version;

    @Column(name = "modified_by")
    protected String modifiedBy;

    @Column(name = "modified_at")
    protected LocalDateTime modifiedAt;

    @Transient
    protected List<GenericEntityAudit> versions = new ArrayList<>();

    public void addVersion(GenericEntityAudit sampleData) {
        versions.add(sampleData);
    }

    public void removeVersion(GenericEntityAudit sampleData) {
        versions.remove(sampleData);
    }

}
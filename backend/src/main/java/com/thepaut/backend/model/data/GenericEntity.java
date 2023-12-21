package com.thepaut.backend.model.data;

import com.thepaut.backend.model.data.audit.GenericEntityAudit;
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
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class GenericEntity<T extends GenericEntityAudit> implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(name = "id")
    protected Long id;

    @Column(name = "version")
    protected Long version = 0L;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Transient
    private List<T> versions = new ArrayList<>();


    public void addVersion(T auditEntity) {
        versions.add(0,auditEntity);
    }

    public void removeVersion(T auditEntity) {
        versions.remove(auditEntity);
    }

}

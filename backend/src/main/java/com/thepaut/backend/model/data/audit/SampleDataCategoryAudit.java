package com.thepaut.backend.model.data.audit;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "sample_data_category_audit")
@IdClass(EntityAuditId.class)
public class SampleDataCategoryAudit extends GenericEntityAudit {

    @Column(name = "category_name")
    private String name;

}

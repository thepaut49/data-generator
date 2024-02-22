package com.thepaut.backend.model.data.audit;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "sample_data_category_audit")
@IdClass(EntityAuditId.class)
public class SampleDataCategoryAudit extends GenericEntityAudit {

    @Column(name = "category_name")
    private String name;

}

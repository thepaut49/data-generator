package com.thepaut.backend.model.data.audit;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "sample_data_audit")
@IdClass(EntityAuditId.class)
public class SampleDataAudit extends GenericEntityAudit {

    @Column(name = "sample_data_key")
    private String key;

    private Long categoryId;

    @Column(name = "sample_data_value")
    private String value;

    @Lob
    @Column(name = "sample_data_blob_value")
    private String blobValue;

    @Column(name = "is_blob_value")
    private boolean isBlobTypeValue;


}
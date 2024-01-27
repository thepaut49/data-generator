package com.thepaut.backend.model.data;

import com.thepaut.backend.model.data.audit.SampleDataAudit;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "sample_data", uniqueConstraints = {
        @UniqueConstraint(name = "SampleDataUniqueKeyAndCategory", columnNames = {"sample_data_key", "sample_data_category_id"})
})
public class SampleData extends GenericEntity<SampleDataAudit>{

    @Column(name = "sample_data_key")
    private String key;

    @ManyToOne
    @JoinColumn(name = "sample_data_category_id", referencedColumnName = "id")
    private SampleDataCategory category;

    @Column(name = "sample_data_value")
    private String value;

    @Lob
    @Column(name = "sample_data_blob_value")
    private String blobValue;

    @Column(name = "is_blob_value")
    private boolean isBlobTypeValue;


    @Transient
    private List<SampleDataAudit> sampleDataVersions = new ArrayList<>();

}
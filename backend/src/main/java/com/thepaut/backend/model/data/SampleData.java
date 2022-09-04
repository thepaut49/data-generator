package com.thepaut.backend.model.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "sample_data", uniqueConstraints = {
        @UniqueConstraint(name = "SampleDataUniqueKeyAndVersionAndCategory", columnNames = {"sample_data_key", "version", "sample_data_category_id"})
})
public class SampleData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "sample_data_id", nullable = false)
    private Long id;

    @Column(name = "sample_data_key")
    private String key;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private SampleDataCategory category;

    @Column(name = "sample_data_value")
    private String value;

    @Lob
    @Column(name = "sample_data_blob_value")
    private String blobValue;

    @Column(name = "is_blob_value")
    private boolean isBlobValue;

    @Column(name = "version")
    private Long version;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Transient
    private List<SampleData> sampleDataVersions;

}
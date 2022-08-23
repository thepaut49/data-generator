package com.thepaut.backend.model.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "sample_data")
public class SampleData {

    @EmbeddedId
    private SampleDataId sampleDataId;

    @Column(name = "sample_data_value")
    private String value;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

}
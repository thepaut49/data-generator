package com.thepaut.backend.model.data;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class SampleDataId implements Serializable {

    @Column(name = "sample_data_key")
    private String key;

    @ManyToOne
    @JoinColumn(name = "sample_data_category_id")
    private SampleDataCategory sampleDataCategory;

}
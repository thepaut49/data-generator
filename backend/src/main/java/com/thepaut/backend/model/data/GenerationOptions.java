package com.thepaut.backend.model.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Setter
@Getter
@Entity
@ToString
@Table(name = "generation_options")
public class GenerationOptions extends GenericEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "field_type")
    @Enumerated(EnumType.STRING)
    private FieldType fieldType;

    @Column(name = "positive_number")
    private Boolean positiveNumber;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id") // Match the column names in SampleDataCategoryId
    private SampleDataCategory sampleDataCategory;

    @ManyToOne
    @JoinColumn(name = "regex_id")
    private Regex regex;

}
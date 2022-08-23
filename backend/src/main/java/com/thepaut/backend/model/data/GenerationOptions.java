package com.thepaut.backend.model.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@ToString
@Table(name = "generation_options", uniqueConstraints = {
        @UniqueConstraint(name = "GOUniqueNameAndVersion", columnNames = {"name", "version"})
})
public class GenerationOptions {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "version")
    private Long version;

    @Column(name = "name")
    private String name;

    @Column(name = "field_type")
    @Enumerated(EnumType.STRING)
    private FieldType fieldType;

    @Column(name = "positive_number")
    private Boolean positiveNumber;

    @ManyToOne
    @JoinColumn(name = "sample_data_category_id")
    private SampleDataCategory sampleDataCategory;

    @ManyToOne
    @JoinColumn(name = "regex_id")
    private Regex regex;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;


}
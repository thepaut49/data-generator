package com.thepaut.backend.model.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "field", uniqueConstraints = {
       // @UniqueConstraint(name = "uc_field_version_api_version", columnNames = {"version", "api_version", "field_name"})
})
public class Field extends GenericEntity{

    @Column(name = "api_version")
    private String apiVersion;

    @Column(name = "field_name")
    private String fieldName;

    @OneToOne
    @JoinColumn(name = "model_id")
    private Model modelOfAssociation;

    @Column(name = "field_type")
    @Enumerated(EnumType.STRING)
    private FieldType fieldType;

    @Column(name = "field_min_size")
    private Integer minSize;

    @Column(name = "field_max_size")
    private Integer maxSize;

}
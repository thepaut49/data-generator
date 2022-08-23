package com.thepaut.backend.model.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "field", uniqueConstraints = {
        @UniqueConstraint(name = "uc_field_version_api_version", columnNames = {"version", "api_version", "field_name"})
})
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "version")
    private Long version;

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

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

}
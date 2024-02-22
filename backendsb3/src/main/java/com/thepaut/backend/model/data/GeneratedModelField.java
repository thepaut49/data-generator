package com.thepaut.backend.model.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name="generated_model_field", uniqueConstraints = {
        @UniqueConstraint(name = "GMFUniqueFieldNameAndVersion", columnNames = {"field_name", "version"})}
)
public class GeneratedModelField {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "version")
    private Long version;

    @Column(name = "field_name")
    private String fieldName;

    @Column(name = "generated_value")
    private String generatedValue;

    @Column(name = "generated_blob_value")
    @Lob
    private String generatedBlobValue;

    @Column(name = "is_blob_value")
    private boolean isBlobValue;

    @ManyToOne
    @JoinColumn(name = "generated_model_id")
    private GeneratedModel generatedModel;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

}
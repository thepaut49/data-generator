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

    @ManyToOne
    @JoinColumn(name = "generated_model_id")
    private GeneratedModel generatedModel;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

}
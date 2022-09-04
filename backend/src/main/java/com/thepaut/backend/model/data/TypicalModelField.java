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
@Table(name="typical_model_field", uniqueConstraints = {
        @UniqueConstraint(name = "TMFUniqueFieldAndVersion", columnNames = {"field_id", "version"})}
)
public class TypicalModelField {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "version")
    private Long version;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "field_id")
    private Field field;

    @Column(name = "fixed_value")
    private String fixedValue;

    @Column(name = "fixed_blob_value")
    @Lob
    private String fixedBlobValue;

    @Column(name = "is_blob_value")
    private boolean isBlobValue;

    @ManyToOne
    @JoinColumn(name = "generation_options_id")
    private GenerationOptions generationOptions;


    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "typical_model_id")
    private TypicalModel typicalModel;

}
package com.thepaut.backend.model.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name="typical_model_field", uniqueConstraints = {
        @UniqueConstraint(name = "TMFUniqueFieldAndVersion", columnNames = {"field_id", "version"})}
)
public class TypicalModelField extends GenericEntity{

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
    @JoinColumn(name = "generation_options_id", referencedColumnName = "id") // Match the column names in SampleDataCategoryId
    private GenerationOptions generationOptions;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "typical_model_id")
    private TypicalModel typicalModel;

}
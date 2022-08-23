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
@Table(name="typical_model_blob_field", uniqueConstraints = {
        @UniqueConstraint(name = "TMBFUniqueFieldAndVersion", columnNames = {"field_id", "version"})
})
public class TypicalModelBlobField {

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
    @Lob
    private String fixedValue;

    @ManyToOne
    @JoinColumn(name = "generation_options_id")
    private GenerationOptions generationOptions;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;


}
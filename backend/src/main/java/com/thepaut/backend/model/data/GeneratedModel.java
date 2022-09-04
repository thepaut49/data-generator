package com.thepaut.backend.model.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name="generated_model", uniqueConstraints = {
        @UniqueConstraint(name = "GMUniqueTypicalModelId", columnNames = {"typical_model_id"})
})
public class GeneratedModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "typical_model_id")
    private TypicalModel typicalModel;
    
    @OneToMany(mappedBy = "generatedModel", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GeneratedModelField> generatedModelFields = new ArrayList<>();

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

}
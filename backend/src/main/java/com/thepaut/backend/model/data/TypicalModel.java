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
@Table(name="typical_model", uniqueConstraints = {
        @UniqueConstraint(name = "TMUniqueNameAndVersion", columnNames = {"name", "version"})
})
public class TypicalModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "version")
    private Long version;

    @Column(name = "name")
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "model_id")
    private Model model;

    @ManyToMany
    @JoinTable(name = "typical_model_fields",
            joinColumns = @JoinColumn(name = "field_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "typical_model_id", referencedColumnName = "id")
    )
    private List<TypicalModelField> fields = new ArrayList<>();

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

}
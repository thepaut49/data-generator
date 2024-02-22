package com.thepaut.backend.model.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name = "model", uniqueConstraints = {
        @UniqueConstraint(name = "ModelUniqueNameAndVersionAndApiVersion", columnNames = {"name", "version", "api_version"})
})
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "version")
    private Long version;

    @Column(name = "api_version")
    private String apiVersion;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(name = "model_fields",
            joinColumns = @JoinColumn(name = "model_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "field_id", referencedColumnName = "id")
    )
    private List<Field> fields = new ArrayList<>();

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;


}
package com.thepaut.backend.model.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@ToString
@Table(name = "regex", uniqueConstraints = {
        @UniqueConstraint(name = "RegexUniqueNameAndVersion", columnNames = {"name", "version"})
})
public class Regex {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "version")
    private Long version;

    @Column(name = "name")
    private String name;

    @Column(name = "format")
    private String format;

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

}
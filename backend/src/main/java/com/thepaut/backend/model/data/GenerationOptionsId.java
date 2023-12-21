package com.thepaut.backend.model.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Setter
@Getter
@Embeddable
public class GenerationOptionsId implements Serializable {

    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "version", nullable = false)
    private Long version;

}
package com.thepaut.backend.model.data.audit;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;


@Data
public class EntityAuditId implements Serializable {

    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "version", nullable = false)
    private Long version;

}

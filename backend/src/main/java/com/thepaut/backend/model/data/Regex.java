package com.thepaut.backend.model.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@ToString
@Table(name = "regex")
public class Regex extends GenericEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "format")
    private String format;

}
package com.thepaut.backend.model.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

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